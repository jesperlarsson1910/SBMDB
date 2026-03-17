package org.example.sbmdb.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReviewDTO(
        @NotNull Long movieId,
        @NotNull Long reviewRating,
        @NotBlank String reviewAuthor,
        String reviewText
) {
}
