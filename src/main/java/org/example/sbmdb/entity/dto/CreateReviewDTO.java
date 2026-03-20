package org.example.sbmdb.entity.dto;

import jakarta.validation.constraints.*;

public record CreateReviewDTO(
        @NotNull Long movieId,
        @NotNull @DecimalMin("0.5") @DecimalMax("5.0") Double reviewRating,
        @NotBlank String reviewAuthor,
        String reviewText
) {}
