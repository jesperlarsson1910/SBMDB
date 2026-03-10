package org.example.sbmdb.repository;

import org.example.sbmdb.entity.Review;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepo extends ListCrudRepository<Review, Long> {

    List<Review> findByMovieId(Long movieId);

    List<Review> findByReviewRating(Long reviewRating);
    List<Review> findByReviewRatingGreaterThanEqual(Long reviewRatingLow);
    List<Review> findByReviewRatingLessThanEqual(Long reviewRatingHigh);
    List<Review> findByReviewRatingBetween(Long reviewRatingLow, Long reviewRatingHigh);

    List<Review> findByReviewAuthor(String reviewAuthor);
}
