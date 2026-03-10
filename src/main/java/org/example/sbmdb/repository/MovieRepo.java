package org.example.sbmdb.repository;

import org.example.sbmdb.entity.Movie;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface MovieRepo extends ListCrudRepository<Movie, Long> {

    List<Movie> findByTitle(String title);
    List<Movie> findByTitleContaining(String title);

    List<Movie> findByDirector(String director);
    List<Movie> findByDirectorContaining(String director);

    List<Movie> findByDescriptionContaining(String description);

    List<Movie> findByRunningTimeGreaterThanEqual(Long runningTimeLow);
    List<Movie> findByRunningTimeLessThanEqual(Long runningTimeHigh);
    List<Movie> findByRunningTimeBetween(Long runningTimeLow, Long runningTimeHigh);

    List<Movie> findByReleaseYear(LocalDate releaseYear);
    List<Movie> findByReleaseYearAfter(LocalDate releaseYearLow);
    List<Movie> findByReleaseYearBefore(LocalDate releaseYearHigh);
    List<Movie> findByReleaseYearBetween(LocalDate releaseYearLow, LocalDate releaseYearHigh);

    List<Movie> findByRatingGreaterThanEqual(Long ratingLow);
    List<Movie> findByRatingLessThanEqual(Long ratingHigh);
    List<Movie> findByRatingBetween(Long ratingLow, Long ratingHigh);
}
