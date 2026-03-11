package org.example.sbmdb.entity.mapper;

import org.example.sbmdb.entity.DTO.CreateMovieDTO;
import org.example.sbmdb.entity.DTO.MovieDTO;
import org.example.sbmdb.entity.DTO.UpdateMovieDTO;
import org.example.sbmdb.entity.Movie;

public class MovieMapper {

    public static Movie createMovie(CreateMovieDTO dto) {
        return new Movie(
                dto.title(),
                dto.directors(),
                dto.description(),
                dto.runningTime(),
                dto.releaseYear()
        );
    }

    public static void updateMovie(Movie movie, UpdateMovieDTO dto){
        movie.setTitle(dto.title());
        movie.setDirectors(dto.directors());
        movie.setDescription(dto.description());
        movie.setRunningTime(dto.runningTime());
        movie.setReleaseYear(dto.releaseYear());
    }

    public static MovieDTO createMovieDTO(Movie movie) {
        return new MovieDTO(
                movie.getTitle(),
                movie.getDirectors(),
                movie.getDescription(),
                movie.getRunningTime(),
                movie.getReleaseYear(),
                movie.getRating(),
                ReviewMapper.createReviewDTO(movie.getReviews())
        );
    }
}
