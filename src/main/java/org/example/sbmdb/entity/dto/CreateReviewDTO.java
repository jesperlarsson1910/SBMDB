package org.example.sbmdb.entity.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReviewDTO(
        @NotNull Long movieId,
        @NotNull @Min(1) @Max(5) Long reviewRating,
        @NotBlank String reviewAuthor,
        String reviewText
) {}
