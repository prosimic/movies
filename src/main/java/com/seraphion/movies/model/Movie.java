package com.seraphion.movies.model;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;

/**
 * query to get movie by title
 * task requires that it matches the name so like is removed
 * like CONCAT('%',:title,'%')
 */
@NamedQueries({
        @NamedQuery(name = "QUERY_GET_MOVIE_BY_TITLE",
                query = "from Movie m where UPPER(m.title) = :title")
})
@Entity
@Table(name = "movies", uniqueConstraints = {
        @UniqueConstraint(name = "uc_movie_id", columnNames = {"id"})
})
public class Movie {
    public static final String QUERY_GET_MOVIE_BY_TITLE
            = "QUERY_GET_MOVIE_BY_TITLE";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, unique = true)
    private Integer id;

    @Column
    private String title;

    //year as string since omdbapi returns year as range of years, e.g. '2002-2007'
    @Column(name="aired_year")
    private String airedYear;

    @Column
    private String type;

    @Column(name="imdbid")
    private String imdbId;

    @Column(name="poster_url")
    private String posterUrl;

    public Movie() {
    }

    public Movie(Integer id, String title, String airedYear, String type, String imdbId, String posterUrl) {
        this.id = id;
        this.title = title;
        this.airedYear = airedYear;
        this.type = type;
        this.imdbId = imdbId;
        this.posterUrl = posterUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAiredYear() {
        return airedYear;
    }

    public void setAiredYear(String airedYear) {
        this.airedYear = airedYear;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", airedYear='" + airedYear + '\'' +
                ", type='" + type + '\'' +
                ", imdbId='" + imdbId + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}
