package com.seraphion.movies.service.mapper;

import com.seraphion.movies.model.Movie;
import com.seraphion.movies.model.Movies;
import com.seraphion.movies.model.Response;
import com.seraphion.movies.model.Search;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface ApiResponseToMovieMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "year", target = "airedYear")
    @Mapping(source = "poster", target = "posterUrl")
    Movie fromSearchToMovie(Search search);

    @Mapping(target = "movies", ignore = true)
    @Mapping(source = "error", target = "errorMessage")
    Movies fromResponseToMovies(Response response);
}
