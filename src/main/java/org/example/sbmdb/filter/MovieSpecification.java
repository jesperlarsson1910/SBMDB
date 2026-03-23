package org.example.sbmdb.filter;

import org.example.sbmdb.entity.Movie;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class MovieSpecification {

    private MovieSpecification() {}

    public static Specification<Movie> hasTitle(String title) {
        return (root, query, cb) -> title == null || title.isBlank() ? null
                : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Movie> hasDirector(String director) {
        return (root, query, cb) -> director == null || director.isBlank() ? null
                : cb.like(cb.lower(root.join("directors")), "%" + director.toLowerCase() + "%");
    }

    public static Specification<Movie> hasDescription(String description) {
        return (root, query, cb) -> description == null || description.isBlank() ? null
                : cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%");
    }

    public static Specification<Movie> runningTimeMin(Long low) {
        return (root, query, cb) -> low == null ? null
                : cb.greaterThanOrEqualTo(root.get("runningTime"), low);
    }

    public static Specification<Movie> runningTimeMax(Long high) {
        return (root, query, cb) -> high == null ? null
                : cb.lessThanOrEqualTo(root.get("runningTime"), high);
    }

    public static Specification<Movie> releaseYearFrom(Integer from) {
        return (root, query, cb) -> from == null ? null
                : cb.greaterThanOrEqualTo(root.get("releaseYear"), LocalDate.of(from, 1, 1));
    }

    public static Specification<Movie> releaseYearTo(Integer to) {
        return (root, query, cb) -> to == null ? null
                : cb.lessThanOrEqualTo(root.get("releaseYear"), LocalDate.of(to, 12, 31));
    }

    public static Specification<Movie> ratingMin(Double low) {
        return (root, query, cb) -> low == null ? null
                : cb.greaterThanOrEqualTo(root.get("rating"), low);
    }

    public static Specification<Movie> ratingMax(Double high) {
        return (root, query, cb) -> high == null ? null
                : cb.lessThanOrEqualTo(root.get("rating"), high);
    }

    public static Specification<Movie> fromFilter(MovieFilter filter) {
        return Specification
                .where(hasTitle(filter.title()))
                .and(hasDirector(filter.director()))
                .and(hasDescription(filter.description()))
                .and(runningTimeMin(filter.runningTimeMin()))
                .and(runningTimeMax(filter.runningTimeMax()))
                .and(releaseYearFrom(filter.releaseYearFrom()))
                .and(releaseYearTo(filter.releaseYearTo()))
                .and(ratingMin(filter.ratingMin()))
                .and(ratingMax(filter.ratingMax()));
    }
}
