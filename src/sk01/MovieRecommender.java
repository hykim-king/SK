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
        log.info("[1] 사용자 선택 기반 영화 추천 시작");
        String selectedCountry = null;
        String selectedGenre = null;
        int yearStart = 0, yearEnd = 0;

        // 나라 선택
        while (true) {
            log.info("나라 선택:");
            for (int i = 0; i < countries.length; i++) {
                System.out.println((i + 1) + ". " + countries[i]);
            }
            System.out.println("0. 뒤로가기");
            System.out.print("번호 입력: ");
            int countryIndex = Integer.parseInt(sc.nextLine());
            if (countryIndex == 0) return;
            if (countryIndex >= 1 && countryIndex <= countries.length) {
                selectedCountry = countries[countryIndex - 1];
                log.info("선택된 나라: " + selectedCountry);
                break;
            }
        }

        // 장르 선택
        while (true) {
            log.info("장르 선택:");
            for (int i = 0; i < genres.length; i++) {
                System.out.println((i + 1) + ". " + genres[i]);
            }
            System.out.println("0. 뒤로가기");
            System.out.print("번호 입력: ");
            int genreIndex = Integer.parseInt(sc.nextLine());
            if (genreIndex == 0) return;
            if (genreIndex >= 1 && genreIndex <= genres.length) {
                selectedGenre = genres[genreIndex - 1];
                log.info("선택된 장르: " + selectedGenre);
                break;
            }
        }

        // 연도 선택
        while (true) {
            log.info("제작 연도 구간 선택:");
            for (int i = 0; i < years.length; i++) {
                System.out.println((i + 1) + ". " + years[i]);
            }
            System.out.println("0. 뒤로가기");
            System.out.print("번호 입력: ");
            int yearGroup = Integer.parseInt(sc.nextLine());
            if (yearGroup == 0) return;
            switch (yearGroup) {
                case 1 -> { yearStart = 1970; yearEnd = 1989; }
                case 2 -> { yearStart = 1990; yearEnd = 1999; }
                case 3 -> { yearStart = 2000; yearEnd = 2009; }
                case 4 -> { yearStart = 2010; yearEnd = 2019; }
                case 5 -> { yearStart = 2020; yearEnd = 2025; }
            }
            log.info("선택된 제작 연도 범위: " + yearStart + " ~ " + yearEnd);
            break;
        }

        List<MovieDTO> movieList = new MovieDAO().getAll();
        List<MovieDTO> filtered = new ArrayList<>();
        for (MovieDTO m : movieList) {
        	
        	String country = m.getCountry().trim();
        	String genre = m.getGenre().trim();
        			
        	boolean countryMatch = false;
        	if ("상관없음".equals(selectedCountry)) {
                countryMatch = true;
            } else if ("중국/대만/홍콩".equals(selectedCountry)) {
               
                countryMatch = country.equalsIgnoreCase("중국") ||
                               country.equalsIgnoreCase("대만") ||
                               country.equalsIgnoreCase("홍콩");
            } else {
                countryMatch = country.equalsIgnoreCase(selectedCountry);
            }

            boolean genreMatch = "상관없음".equals(selectedGenre) || genre.equalsIgnoreCase(selectedGenre);
            boolean yearMatch = m.getYear() >= yearStart && m.getYear() <= yearEnd;
            if (countryMatch && genreMatch && yearMatch) {
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
            System.out.println("러닝타임   : " + result.getRuntime() + "분");
            System.out.println("감독       : " + result.getDirector());
            System.out.println("======================================================\n");
        }
    }
}
