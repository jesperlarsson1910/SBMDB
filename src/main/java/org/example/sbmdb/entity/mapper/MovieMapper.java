package org.example.sbmdb.entity.mapper;

import org.example.sbmdb.entity.DTO.CreateMovieDTO;
import org.example.sbmdb.entity.DTO.MovieDTO;
import org.example.sbmdb.entity.DTO.MovieSummaryDTO;
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
        if (dto.title() != null && !dto.title().isBlank()) movie.setTitle(dto.title());
        if (dto.directors() != null && !dto.directors().isEmpty()) movie.setDirectors(dto.directors());
        if (dto.runningTime() != null) movie.setRunningTime(dto.runningTime());
        if (dto.releaseYear() != null) movie.setReleaseYear(dto.releaseYear());
        if (dto.description() != null) movie.setDescription(dto.description().orElse(null));
    }

    public static MovieDTO createMovieDTO(Movie movie) {
        return new MovieDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getDirectors(),
                movie.getDescription(),
                movie.getRunningTime(),
                movie.getReleaseYear(),
                movie.getRating(),
                ReviewMapper.createReviewDTO(movie.getReviews())
        );
    }

    public static MovieSummaryDTO createMovieSummaryDTO(Movie movie) {
        return new MovieSummaryDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getDirectors(),
                movie.getReleaseYear(),
                movie.getRating()
        );
    }
}
