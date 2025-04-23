package com.pcwk.ehr.ed01;

import java.io.*;
import java.util.*;

public class MainAdminDao {

    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PW = "1234";
    private static final String FILE_PATH = ".\\data\\MovieList.csv";

    static class Movie {
        private String title;
        private int year;
        private String country;
        private String runtime;
        private String genre;
        private String director;

        public Movie(String title, int year, String country, String runtime, String genre, String director) {
            this.title = title;
            this.year = year;
            this.country = country;
            this.runtime = runtime;
            this.genre = genre;
            this.director = director;
        }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public int getYear() { return year; }
        public void setYear(int year) { this.year = year; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public String getRuntime() { return runtime; }
        public void setRuntime(String runtime) { this.runtime = runtime; }
        public String getGenre() { return genre; }
        public void setGenre(String genre) { this.genre = genre; }
        public String getDirector() { return director; }
        public void setDirector(String director) { this.director = director; }

        @Override
        public String toString() {
            return String.format("%s,%d,%s,%s,%s,%s",
                    title, year, country, runtime, genre, director);
        }
    }

    static class AdminDao {
        private List<Movie> movieList = new ArrayList<>();

        public AdminDao() {
            loadFromFile();
        }

        private void loadFromFile() {
            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                boolean isFirstLine = true;
                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }
                    String[] tokens = line.split(",");
                    if (tokens.length == 6) {
                        movieList.add(new Movie(
                            tokens[0].trim(),
                            Integer.parseInt(tokens[1].trim()),
                            tokens[2].trim(),
                            tokens[3].trim(),
                            tokens[4].trim(),
                            tokens[5].trim()
                        ));
                    }
                }
            } catch (IOException e) {
                System.out.println("❌ CSV 파일 로드 오류: " + e.getMessage());
            }
        }

        private void saveToFile() {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                bw.write("제목,연도,나라,유형,장르,감독");
                bw.newLine();
                for (Movie m : movieList) {
                    bw.write(m.toString());
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("❌ CSV 저장 오류: " + e.getMessage());
            }
        }

        public List<Movie> getAll() {
            return movieList;
        }

        public Movie findByTitle(String title) {
            for (Movie m : movieList) {
                if (m.getTitle().equals(title)) return m;
            }
            return null;
        }

        public void update(Movie movie) {
            saveToFile();
        }

        public boolean delete(String title) {
            boolean removed = movieList.removeIf(m -> m.getTitle().equals(title));
            if (removed) saveToFile();
            return removed;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("🔐 관리자 ID: ");
        if (!ADMIN_ID.equals(sc.nextLine())) {
            System.out.println("❌ ID 오류"); 
            return;
        }
        System.out.print("🔐 비밀번호: ");
        if (!ADMIN_PW.equals(sc.nextLine())) {
            System.out.println("❌ 비밀번호 오류"); 
            return;
        }

        AdminDao dao = new AdminDao();
        boolean running = true;

        while (running) {
            System.out.println("\n📋 관리자 메뉴");
            System.out.println("1. 전체 목록 보기");
            System.out.println("2. 영화 수정");
            System.out.println("3. 영화 삭제");
            System.out.println("4. 종료");
            System.out.print("선택: ");

            switch (sc.nextLine()) {
                case "1":
                    for (Movie m : dao.getAll()) System.out.println(m);
                    break;
                case "2":
                    System.out.print("수정할 영화 제목: ");
                    String title = sc.nextLine();
                    Movie movie = dao.findByTitle(title);
                    if (movie == null) {
                        System.out.println("❌ 영화 없음"); break;
                    }
                    System.out.print("새 제목: "); movie.setTitle(sc.nextLine());
                    System.out.print("새 연도: "); movie.setYear(Integer.parseInt(sc.nextLine()));
                    System.out.print("새 나라: "); movie.setCountry(sc.nextLine());
                    System.out.print("새 유형: "); movie.setRuntime(sc.nextLine());
                    System.out.print("새 장르: "); movie.setGenre(sc.nextLine());
                    System.out.print("새 감독: "); movie.setDirector(sc.nextLine());
                    dao.update(movie);
                    System.out.println("✅ 수정 완료");
                    break;
                case "3":
                    System.out.print("삭제할 영화 제목: ");
                    if (dao.delete(sc.nextLine())) {
                        System.out.println("🗑️ 삭제 완료");
                    } else {
                        System.out.println("❌ 삭제 실패");
                    }
                    break;
                case "4":
                    running = false;
                    System.out.println("👋 종료합니다");
                    break;
                default:
                    System.out.println("❗ 잘못된 입력");
            }
        }

        sc.close();
    }
}