package org.example.sbmdb.repository;

import aj.org.objectweb.asm.commons.Remapper;
import org.example.sbmdb.entity.Movie;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface MovieRepo extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    boolean existsByTitleIgnoreCaseAndDirectorsIgnoreCaseAndReleaseYear(String title, List<String> directors, LocalDate releaseYear);

    default boolean isUnique(Movie movie){
        return !existsByTitleIgnoreCaseAndDirectorsIgnoreCaseAndReleaseYear(movie.getTitle(), movie.getDirectors(), movie.getReleaseYear());
    }

}
