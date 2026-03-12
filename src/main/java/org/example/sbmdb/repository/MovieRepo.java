package org.example.sbmdb.repository;

import org.example.sbmdb.entity.Movie;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface MovieRepo extends ListCrudRepository<Movie, Long> {

    List<Movie> findByTitle(String title, Pageable pageable);
    List<Movie> findByTitleContaining(String title, Pageable pageable);

    List<Movie> findByDirectors(List<String> directors, Pageable pageable);
    List<Movie> findByDirectorsContaining(String director, Pageable pageable);

    List<Movie> findByDescriptionContaining(String description, Pageable pageable);

    List<Movie> findByRunningTimeGreaterThanEqual(Long runningTimeLow, Pageable pageable);
    List<Movie> findByRunningTimeLessThanEqual(Long runningTimeHigh, Pageable pageable);
    List<Movie> findByRunningTimeBetween(Long runningTimeLow, Long runningTimeHigh, Pageable pageable);

    List<Movie> findByReleaseYear(LocalDate releaseYear, Pageable pageable);

    List<Movie> findByReleaseYearGreaterThanEqual(LocalDate releaseYearLow, Pageable pageable);
    List<Movie> findByReleaseYearLessThanEqual(LocalDate releaseYearHigh, Pageable pageable);
    List<Movie> findByReleaseYearBetween(LocalDate releaseYearLow, LocalDate releaseYearHigh, Pageable pageable);

    List<Movie> findByRatingGreaterThanEqual(Long ratingLow, Pageable pageable);
    List<Movie> findByRatingLessThanEqual(Long ratingHigh, Pageable pageable);
    List<Movie> findByRatingBetween(Long ratingLow, Long ratingHigh, Pageable pageable);

    boolean existsByTitleIgnoreCaseAndDirectorsIgnoreCaseAndReleaseYear(String title, List<String> directors, LocalDate releaseYear);

    default boolean isUnique(Movie movie){
        return existsByTitleIgnoreCaseAndDirectorsIgnoreCaseAndReleaseYear(movie.getTitle(), movie.getDirectors(), movie.getReleaseYear());
    }
}
