package com.pcwk.ehr.admin.vo;

public class MovieVO {

    private String title;
    private int year;
    private String country;
    private String runtime;
    private String genre;
    private String director;

    public MovieVO() {}

    public MovieVO(String title, int year, String country, String runtime, String genre, String director) {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "MovieVO [title=" + title + ", year=" + year + ", country=" + country +
               ", runtime=" + runtime + ", genre=" + genre + ", director=" + director + "]";
    }
} 
