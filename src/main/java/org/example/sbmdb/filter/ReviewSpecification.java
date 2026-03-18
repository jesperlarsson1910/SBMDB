package org.example.sbmdb.filter;

import org.example.sbmdb.entity.Review;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpecification {

    public static Specification<Review> hasMovieId(Long movieId) {
        return (root, query, cb) -> movieId == null ? null
                : cb.equal(root.get("movie").get("id"), movieId);
    }

    public static Specification<Review> ratingMin(Double low) {
        return (root, query, cb) -> low == null ? null
                : cb.greaterThanOrEqualTo(root.get("reviewRating"), low);
    }

    public static Specification<Review> ratingMax(Double high) {
        return (root, query, cb) -> high == null ? null
                : cb.lessThanOrEqualTo(root.get("reviewRating"), high);
    }

    public static Specification<Review> hasAuthor(String author) {
        return (root, query, cb) -> author == null || author.isBlank() ? null
                : cb.like(cb.lower(root.get("reviewAuthor")), "%" + author.toLowerCase() + "%");
    }

    public static Specification<Review> fromFilter(ReviewFilter filter) {
        return Specification
                .where(hasMovieId(filter.movieId()))
                .and(ratingMin(filter.ratingMin()))
                .and(ratingMax(filter.ratingMax()))
                .and(hasAuthor(filter.author()));
    }
}
