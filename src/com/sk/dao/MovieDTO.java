package com.sk.dao;

public class MovieDTO {
    private String title;
    private int year;
    private String country;
    private String runtime;
    private String genre;
    private String director;

    public MovieDTO(String title, int year, String country, String runtime, String genre, String director) {
        this.title = title;
        this.year = year;
        this.country = country;
        this.runtime = runtime;
        this.genre = genre;
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getCountry() {
        return country;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }
}
