package org.example.sbmdb.entity.dto;

import java.util.Optional;

public record UpdateReviewDTO(
        Long id,
        Long reviewRating,
        String reviewAuthor,
        Optional<String> reviewText
) {
}
