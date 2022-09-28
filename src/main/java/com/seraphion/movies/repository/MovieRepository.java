package com.seraphion.movies.repository;

import com.seraphion.movies.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
}
