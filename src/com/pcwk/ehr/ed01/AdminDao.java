package com.pcwk.ehr.ed01;

import java.io.*;
import java.util.*;
import com.pcwk.ehr.cmn.Workdiv;

public class AdminDao implements Workdiv<AdminDao.Movie> {
    private static final String FILE_PATH = ".\\data\\MovieList.csv";
    private List<Movie> movieList = new ArrayList<>();

    public static class Movie {
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

    public AdminDao() {
        loadFromFile();
    }

    private void loadFromFile() {
        movieList.clear();
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

    // Workdiv 인터페이스 구현부

    @Override
    public int doSave(Movie dto) {
        movieList.add(dto);
        saveToFile();
        return 1;
    }

    @Override
    public int doUpdate(Movie dto) {
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getTitle().equals(dto.getTitle())) {
                movieList.set(i, dto);
                saveToFile();
                return 1;
            }
        }
        return 0;
    }

    @Override
    public int doDelete(String title) {
        boolean removed = movieList.removeIf(m -> m.getTitle().equals(title));
        if (removed) saveToFile();
        return removed ? 1 : 0;
    }

    @Override
    public Movie doSelectOne(String title) {
        for (Movie m : movieList) {
            if (m.getTitle().equals(title)) return m;
        }
        return null;
    }

    @Override
    public List<Movie> doRetrieve() {
        return movieList;
    }
}