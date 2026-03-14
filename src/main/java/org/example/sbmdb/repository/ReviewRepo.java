package org.example.sbmdb.repository;

import org.example.sbmdb.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepo extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    boolean existsByReviewAuthorIgnoreCaseAndMovieId(String reviewAuthor, Long movieId);

    default boolean isUnique(Review review) {
        return !existsByReviewAuthorIgnoreCaseAndMovieId(review.getReviewAuthor(), review.getMovie().getId());
    }
}
