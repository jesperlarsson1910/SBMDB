package org.example.sbmdb.repository;

import org.example.sbmdb.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface MovieRepo extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    @Query("SELECT COUNT(m) > 0 FROM Movie m WHERE LOWER(m.title) = LOWER(:title) AND YEAR(m.releaseYear) = YEAR(:releaseYear)")
    boolean existsByTitleIgnoreCaseAndReleaseYear(@Param("title") String title, @Param("releaseYear") LocalDate releaseYear);

    default boolean isUnique(Movie movie){
        return !existsByTitleIgnoreCaseAndReleaseYear(movie.getTitle(), movie.getReleaseYear());
    }

}
