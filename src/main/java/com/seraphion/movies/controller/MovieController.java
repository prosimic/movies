package com.seraphion.movies.controller;

import com.seraphion.movies.model.Movie;
import com.seraphion.movies.model.Movies;
import com.seraphion.movies.service.MovieService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("api")
public class MovieController {
    private final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);
    private final String ENTITY_NAME = "MovieController";
    @Value("${api.x-auth}")
    private String XAUTH;

    @Autowired
    MovieService movieService;

    @GetMapping(value = "/movies", produces = "application/json")
    private ResponseEntity<List<Movie>> getMoviesByTitle(@RequestParam(required = false) String title) {
        if (StringUtils.isBlank(title)) {
            return ResponseEntity.status(HttpStatus.OK).body(movieService.getAllMovies());
        }
        Movies movies = movieService.getMoviesByParam(title);
        if(StringUtils.isNotBlank(movies.getErrorMessage())) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(movies.getMovies());
    }

    @GetMapping(value = "/movie/{id}", produces = "application/json")
    private Movie getMovieById(@RequestHeader("x-auth") String xAuth, @PathVariable(value = "id") Integer id) throws AccessDeniedException {
        LOGGER.debug("find by criteria : {}", id);
        if(!xAuth.equals(XAUTH)){
            LOGGER.error(ENTITY_NAME + " - unauthorized call to getMovieById!");
            throw new AccessDeniedException("Unauthorized call");
        }

        if (id == null) {
            LOGGER.error("Missing id in url!");
            throw new IllegalArgumentException();
        }
        return movieService.getMovieById(id);
    }
}
