package org.example.sbmdb.repository;

import org.example.sbmdb.entity.Review;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepo extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    List<Review> findByMovieId(Long movieId, Sort sort);

    boolean existsByReviewAuthorIgnoreCaseAndMovieId(String reviewAuthor, Long movieId);

    default boolean isUnique(Review review) {
        return !existsByReviewAuthorIgnoreCaseAndMovieId(review.getReviewAuthor(), review.getMovie().getId());
    }
}
