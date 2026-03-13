package org.example.sbmdb.repository;

import org.example.sbmdb.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepo extends ListCrudRepository<Review, Long> {

    Page<Review> findByMovieId(Long movieId, Pageable pageable);

    Page<Review> findByReviewRating(Long reviewRating, Pageable pageable);
    Page<Review> findByReviewRatingGreaterThanEqual(Long reviewRatingLow, Pageable pageable);
    Page<Review> findByReviewRatingLessThanEqual(Long reviewRatingHigh, Pageable pageable);
    Page<Review> findByReviewRatingBetween(Long reviewRatingLow, Long reviewRatingHigh, Pageable pageable);

    Page<Review> findByReviewAuthor(String reviewAuthor, Pageable pageable);

    boolean existsByReviewAuthorIgnoreCaseAndMovieId(String reviewAuthor, Long movieId);

    default boolean isUnique(Review review) {
        return !existsByReviewAuthorIgnoreCaseAndMovieId(review.getReviewAuthor(), review.getMovie().getId());
    }
}
