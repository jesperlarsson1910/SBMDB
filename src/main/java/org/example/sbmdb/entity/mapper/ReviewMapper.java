package org.example.sbmdb.entity.mapper;

import org.example.sbmdb.entity.DTO.CreateReviewDTO;
import org.example.sbmdb.entity.DTO.ReviewDTO;
import org.example.sbmdb.entity.DTO.UpdateReviewDTO;
import org.example.sbmdb.entity.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewMapper {

    public static Review createReview(CreateReviewDTO dto) {
        return new Review(
                dto.movie(),
                dto.reviewRating(),
                dto.reviewAuthor(),
                dto.reviewText()
        );
    }

    public static void updateReview(Review review, UpdateReviewDTO dto) {
        review.setReviewRating(dto.reviewRating());
        review.setReviewAuthor(dto.reviewAuthor());
        review.setReviewUpdateDate(dto.reviewUpdateDate());
    }

    public static ReviewDTO createReviewDTO(Review review) {
        return new ReviewDTO(
                review.getMovie(),
                review.getReviewRating(),
                review.getReviewAuthor(),
                review.getReviewText(),
                review.getReviewDate(),
                review.getReviewUpdateDate()
        );
    }

    public static List<ReviewDTO> createReviewDTO(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewMapper::createReviewDTO)
                .toList();
    }
}
