package org.example.sbmdb.entity.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Optional;

public record UpdateReviewDTO(
        Long id,
        @Min(1) @Max(5) Long reviewRating,
        String reviewAuthor,
        Optional<String> reviewText
) {}
