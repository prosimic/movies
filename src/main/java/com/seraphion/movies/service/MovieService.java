package com.seraphion.movies.service;

import com.seraphion.movies.model.Movie;
import com.seraphion.movies.model.Movies;
import com.seraphion.movies.model.Response;
import com.seraphion.movies.repository.MovieRepository;
import com.seraphion.movies.service.mapper.ApiResponseToMovieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
    @Value("${omdbapi.key}")
    private String OMDBKEY;
    @Value("${omdbapi.url}")
    private String OMDBURL;
    @PersistenceContext
    EntityManager em;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    private ApiResponseToMovieMapper apiResponseToMovieMapper;

    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        movieRepository.findAll().forEach(movie -> movieList.add(movie));
        return movieList;
    }

    /**
     * @param title - movie title
     * search by @NamedQuery
     */
    public Movies getMoviesByParam(String title) {
        Movies movies = new Movies();
        List<Movie> movieList;

        //database check
        Query query = em.createNamedQuery(Movie.QUERY_GET_MOVIE_BY_TITLE)
                .setParameter("title", title.toUpperCase());
        movieList = query.getResultList();
        movies.setMovies(movieList);

        //url check if database return no results
        if(movieList.isEmpty()) {
            LOGGER.info("Getting data from url!");
          movies = getOnlineData(title, movies);
        }
        return movies;
    }

    /**
     * @param id - movie id
     * search by CriteriaBuilder
     */
    public Movie getMovieById(Integer id) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
            Root<Movie> movieRoot = cq.from(Movie.class);
            Predicate predicateForMovieName
                    = cb.equal(movieRoot.get("id"), id);
            cq.where(predicateForMovieName);
            Movie movie = em.createQuery(cq).getSingleResult();
            return movie;
        } catch(NoResultException e) {
            LOGGER.debug("find by criteria : {}, error: {}", id, e.getMessage());
            return  null;
        }
    }

    /**
     * Used for getting online data based on param, received data is stored to database
     * Returns errorMessage instead throwing exception
     * @param title - searchParam passed to online api
     * @param movies - movie response
     */
    private Movies getOnlineData(String title, Movies movies) {
        List<Movie> movieList = movies.getMovies();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.CONTENT_TYPE, "text/plain; charset=utf-8");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(OMDBURL)
                .queryParam("apiKey", OMDBKEY)
                .queryParam("s",title); //required search parameter


        ResponseEntity<Response> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, Response.class);
        if(response.getBody().getResponse().equals("True")) {
            response.getBody().getSearch().forEach(search -> {
                Movie movie = apiResponseToMovieMapper.fromSearchToMovie(search);
                movieRepository.save(movie);
                movieList.add(movie);
            });
        } else {
            movies = apiResponseToMovieMapper.fromResponseToMovies(response.getBody());
        }
        return movies;
    }
}
