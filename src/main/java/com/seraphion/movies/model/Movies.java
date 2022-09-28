package com.seraphion.movies.model;

import java.util.List;

public class Movies {
    private List<Movie> movies;
    private String errorMessage;

    public Movies() {
    }

    public Movies(List<Movie> movies, String errorMessage) {
        this.movies = movies;
        this.errorMessage = errorMessage;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
