package org.example.sbmdb.repository;

import org.example.sbmdb.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface MovieRepo extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    boolean existsByTitleIgnoreCaseAndDirectorsIgnoreCaseAndReleaseYear(String title, List<String> directors, LocalDate releaseYear);

    default boolean isUnique(Movie movie){
        return !existsByTitleIgnoreCaseAndDirectorsIgnoreCaseAndReleaseYear(movie.title(), movie.directors(), movie.getReleaseYear());
    }

}
