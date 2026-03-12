package org.example.sbmdb.entity.mapper;

import org.example.sbmdb.entity.DTO.CreateReviewDTO;
import org.example.sbmdb.entity.DTO.ReviewDTO;
import org.example.sbmdb.entity.DTO.UpdateReviewDTO;
import org.example.sbmdb.entity.Movie;
import org.example.sbmdb.entity.Review;

import java.time.LocalDate;
import java.util.List;

public class ReviewMapper {

    public static Review createReview(Movie movie, CreateReviewDTO dto) {
        return new Review(
                movie,
                dto.reviewRating(),
                dto.reviewAuthor(),
                dto.reviewText()
        );
    }

    public static void updateReview(Review review, UpdateReviewDTO dto) {
        if (dto.reviewRating() != null) review.setReviewRating(dto.reviewRating());
        if (dto.reviewAuthor() != null && !dto.reviewAuthor().isBlank()) review.setReviewAuthor(dto.reviewAuthor());
        dto.reviewText().ifPresent(value -> review.setReviewText(value.isBlank() ? null : value));
        review.setReviewUpdateDate(LocalDate.now());
    }

    public static ReviewDTO createReviewDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getMovie().getId(),
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
