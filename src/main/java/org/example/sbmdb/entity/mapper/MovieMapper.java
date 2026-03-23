package org.example.sbmdb.entity.mapper;

import org.example.sbmdb.entity.Review;
import org.example.sbmdb.entity.dto.CreateMovieDTO;
import org.example.sbmdb.entity.dto.MovieDTO;
import org.example.sbmdb.entity.dto.MovieSummaryDTO;
import org.example.sbmdb.entity.dto.UpdateMovieDTO;
import org.example.sbmdb.entity.Movie;

import java.util.List;

public class MovieMapper {

    private MovieMapper() {}

    public static Movie createMovie(CreateMovieDTO dto) {
        return new Movie(
                dto.title(),
                dto.directors(),
                dto.description(),
                dto.runningTime(),
                dto.releaseYear()
        );
    }

    @SuppressWarnings("OptionalAssignedToNull")
    public static void updateMovie(Movie movie, UpdateMovieDTO dto){
        if (dto.title() != null && !dto.title().isBlank()) movie.setTitle(dto.title());
        if (dto.directors() != null && !dto.directors().isEmpty()) movie.setDirectors(dto.directors());
        if (dto.runningTime() != null) movie.setRunningTime(dto.runningTime());
        if (dto.releaseYear() != null) movie.setReleaseYear(dto.releaseYear());
        //noinspection OptionalAssignedToNull
        if (dto.description() != null) movie.setDescription(dto.description().orElse(null));
    }

    public static MovieDTO createMovieDTO(Movie movie) {
        return createMovieDTO(movie, movie.getReviews());
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

    public static MovieDTO createMovieDTO(Movie movie, List<Review> reviews) {
        return new MovieDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getDirectors(),
                movie.getDescription(),
                movie.getRunningTime(),
                movie.getReleaseYear(),
                movie.getRating(),
                reviews.stream().map(ReviewMapper::createReviewDTO).toList()
        );
    }
}
