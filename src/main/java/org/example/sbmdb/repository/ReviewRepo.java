package org.example.sbmdb.repository;

import org.example.sbmdb.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepo extends ListCrudRepository<Review, Long> {

    List<Review> findByMovieId(Long movieId, Pageable pageable);

    List<Review> findByReviewRating(Long reviewRating, Pageable pageable);
    List<Review> findByReviewRatingGreaterThanEqual(Long reviewRatingLow, Pageable pageable);
    List<Review> findByReviewRatingLessThanEqual(Long reviewRatingHigh, Pageable pageable);
    List<Review> findByReviewRatingBetween(Long reviewRatingLow, Long reviewRatingHigh, Pageable pageable);

    List<Review> findByReviewAuthor(String reviewAuthor, Pageable pageable);
}
