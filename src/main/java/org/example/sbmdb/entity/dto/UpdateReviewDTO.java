package org.example.sbmdb.entity.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.util.Optional;

public record UpdateReviewDTO(
        Long id,
        @DecimalMin("0.5") @DecimalMax("5.0") Double reviewRating,
        String reviewAuthor,
        Optional<String> reviewText
) {}
