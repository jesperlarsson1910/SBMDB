package org.example.sbmdb.entity.mapper;

import org.example.sbmdb.entity.dto.CreateReviewDTO;
import org.example.sbmdb.entity.dto.ReviewDTO;
import org.example.sbmdb.entity.dto.UpdateReviewDTO;
import org.example.sbmdb.entity.Movie;
import org.example.sbmdb.entity.Review;

public class ReviewMapper {

    private ReviewMapper() {}

    public static Review createReview(Movie movie, CreateReviewDTO dto) {
        return new Review(
                movie,
                dto.reviewRating(),
                dto.reviewAuthor(),
                dto.reviewText()
        );
    }

    @SuppressWarnings("OptionalAssignedToNull")
    public static void updateReview(Review review, UpdateReviewDTO dto) {
        if (dto.reviewRating() != null) review.setReviewRating(dto.reviewRating());
        if (dto.reviewAuthor() != null && !dto.reviewAuthor().isBlank()) review.setReviewAuthor(dto.reviewAuthor());
        //noinspection OptionalAssignedToNull
        if (dto.reviewText() != null) review.setReviewText(dto.reviewText().orElse(null));
    }

    public static ReviewDTO createReviewDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getMovie().getId(),
                review.getMovie().getTitle(),
                review.getReviewRating(),
                review.getReviewAuthor(),
                review.getReviewText(),
                review.getReviewDate(),
                review.getReviewUpdateDate()
        );
    }

}
