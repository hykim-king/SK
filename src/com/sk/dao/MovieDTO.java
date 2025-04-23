package com.sk.dao;

public class MovieDTO {
    private String title;
    private int year;
    private String country;
    private String runtime;
    private String genre;
    private String director;

    // 전체 필드용 생성자
    public MovieDTO(String title, int year, String country, String runtime, String genre, String director) {
        this.title = title;
        this.year = year;
        this.country = country;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
    }

    // 제목만 받는 생성자 → 검색/삭제용
    public MovieDTO(String title) {
        this.title = title;
    }

    // Getter/Setter 생략 가능
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
        return String.join(", ", title, String.valueOf(year), country, runtime, genre, director);
    }
}
