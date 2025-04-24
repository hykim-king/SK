package sk01;

import java.util.*;
import java.util.logging.*;
import com.sk.dao.MovieDAO;
import com.sk.dao.MovieDTO;

public class MovieRecommender {
    static Scanner sc = new Scanner(System.in);
    private static final Logger log = Logger.getLogger(MovieRecommender.class.getName());

    static {
        LogManager.getLogManager().reset();
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        log.addHandler(handler);
        log.setLevel(Level.ALL);
    }

    static String[] countries = { "한국", "일본", "중국/대만/홍콩", "미국", "영국", "프랑스", "독일", "기타", "상관없음" };
    static String[] genres = { "가족", "공포/호러", "다큐멘터리", "드라마", "멜로/로맨스", "미스터리/스릴러", "범죄", "사극", "애니메이션", "액션/서부극", "전쟁", "코미디", "SF/판타지", "상관없음" };
    static String[] years = { "70~80년대", "90~99년", "00~09년", "10~19년", "20~25년", "상관없음" };

    public static void main(String[] args) {
        while (true) {
            recommendMovie();
        }
    }

    static void recommendMovie() {
        String selectedCountry = null;
        String selectedGenre = null;
        int yearStart = 0, yearEnd = 0;
        String runtimeType = "상관없음"; // "단편", "장편", "상관없음"

        int step = 1;
        while (step <= 4) {
            switch (step) {
                case 1 -> {
                    log.info("[1] 나라 선택:");
                    for (int i = 0; i < countries.length; i++) System.out.println((i + 1) + ". " + countries[i]);
                    System.out.println("0. 종료");
                    int input = Integer.parseInt(sc.nextLine());
                    if (input == 0) return;
                    if (input >= 1 && input <= countries.length) {
                        selectedCountry = countries[input - 1];
                        step++;
                    }
                }
                case 2 -> {
                    log.info("[2] 장르 선택:");
                    for (int i = 0; i < genres.length; i++) System.out.println((i + 1) + ". " + genres[i]);
                    System.out.println("0. 뒤로가기");
                    int input = Integer.parseInt(sc.nextLine());
                    if (input == 0) { step--; continue; }
                    if (input >= 1 && input <= genres.length) {
                        selectedGenre = genres[input - 1];
                        step++;
                    }
                }
                case 3 -> {
                    log.info("[3] 러닝타임 선택:");
                    System.out.println("1. 단편 (40분 이하)");
                    System.out.println("2. 장편 (41분 이상)");
                    System.out.println("3. 상관없음");
                    System.out.println("0. 뒤로가기");
                    int input = Integer.parseInt(sc.nextLine());
                    if (input == 0) { step--; continue; }
                    if (input == 1) { runtimeType = "단편"; step++; }
                    else if (input == 2) { runtimeType = "장편"; step++; }
                    else if (input == 3) { runtimeType = "상관없음"; step++; }
                }
                case 4 -> {
                    log.info("[4] 제작 연도 구간 선택:");
                    for (int i = 0; i < years.length; i++) System.out.println((i + 1) + ". " + years[i]);
                    System.out.println("0. 뒤로가기");
                    int input = Integer.parseInt(sc.nextLine());
                    if (input == 0) { step--; continue; }
                    if (input >= 1 && input <= years.length) {
                        String selectedYear = years[input - 1];
                        if ("상관없음".equals(selectedYear)) {
                            yearStart = Integer.MIN_VALUE;
                            yearEnd = Integer.MAX_VALUE;
                        } else {
                            switch (input) {
                                case 1 -> { yearStart = 1970; yearEnd = 1989; }
                                case 2 -> { yearStart = 1990; yearEnd = 1999; }
                                case 3 -> { yearStart = 2000; yearEnd = 2009; }
                                case 4 -> { yearStart = 2010; yearEnd = 2019; }
                                case 5 -> { yearStart = 2020; yearEnd = 2025; }
                            }
                        }
                        step++;
                    }
                }
            }
        }

        List<MovieDTO> movieList = new MovieDAO().getAll();
        List<MovieDTO> filtered = new ArrayList<>();

        for (MovieDTO m : movieList) {
            String country = m.getCountry().trim();
            String genre = m.getGenre().trim();
            String runtime = m.getRuntime().trim();

            boolean countryMatch = "상관없음".equals(selectedCountry) ||
                ("중국/대만/홍콩".equals(selectedCountry) &&
                    (country.equalsIgnoreCase("중국") || country.equalsIgnoreCase("대만") || country.equalsIgnoreCase("홍콩"))) ||
                country.equalsIgnoreCase(selectedCountry);

            boolean genreMatch = "상관없음".equals(selectedGenre) || genre.equalsIgnoreCase(selectedGenre);
            boolean yearMatch = m.getYear() >= yearStart && m.getYear() <= yearEnd;
            boolean runtimeMatch = "상관없음".equals(runtimeType) || runtime.contains(runtimeType);

            if (countryMatch && genreMatch && yearMatch && runtimeMatch) {
                filtered.add(m);
            }
        }

        if (filtered.isEmpty()) {
            log.warning(":x: 조건에 맞는 영화가 없습니다.");
        } else {
            MovieDTO result = filtered.get(new Random().nextInt(filtered.size()));
            System.out.println("\n==================== 🎬 추천 영화 ====================");
            System.out.println("제목       : " + result.getTitle());
            System.out.println("제작년도   : " + result.getYear());
            System.out.println("국가       : " + result.getCountry());
            System.out.println("장르       : " + result.getGenre());
            System.out.println("러닝타임   : " + result.getRuntime());
            System.out.println("감독       : " + result.getDirector());
            System.out.println("======================================================\n");
        }
    }
}
