package org.example.sbmdb.repository;

import aj.org.objectweb.asm.commons.Remapper;
import org.example.sbmdb.entity.Movie;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface MovieRepo extends ListCrudRepository<Movie, Long> {

    Page<Movie> findAll(Pageable pageable);

    Page<Movie> findByTitle(String title, Pageable pageable);
    Page<Movie> findByTitleContaining(String title, Pageable pageable);

    Page<Movie> findByDirectors(List<String> directors, Pageable pageable);
    Page<Movie> findByDirectorsContaining(String director, Pageable pageable);

    Page<Movie> findByDescriptionContaining(String description, Pageable pageable);

    Page<Movie> findByRunningTimeGreaterThanEqual(Long runningTimeLow, Pageable pageable);
    Page<Movie> findByRunningTimeLessThanEqual(Long runningTimeHigh, Pageable pageable);
    Page<Movie> findByRunningTimeBetween(Long runningTimeLow, Long runningTimeHigh, Pageable pageable);

    Page<Movie> findByReleaseYear(LocalDate releaseYear, Pageable pageable);

    Page<Movie> findByReleaseYearGreaterThanEqual(LocalDate releaseYearLow, Pageable pageable);
    Page<Movie> findByReleaseYearLessThanEqual(LocalDate releaseYearHigh, Pageable pageable);
    Page<Movie> findByReleaseYearBetween(LocalDate releaseYearLow, LocalDate releaseYearHigh, Pageable pageable);

    Page<Movie> findByRatingGreaterThanEqual(Double ratingLow, Pageable pageable);
    Page<Movie> findByRatingLessThanEqual(Double ratingHigh, Pageable pageable);
    Page<Movie> findByRatingBetween(Double low, Double high, Pageable pageable);

    boolean existsByTitleIgnoreCaseAndDirectorsIgnoreCaseAndReleaseYear(String title, List<String> directors, LocalDate releaseYear);

    default boolean isUnique(Movie movie){
        return !existsByTitleIgnoreCaseAndDirectorsIgnoreCaseAndReleaseYear(movie.getTitle(), movie.getDirectors(), movie.getReleaseYear());
    }

}
