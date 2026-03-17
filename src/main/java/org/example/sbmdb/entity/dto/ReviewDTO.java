package org.example.sbmdb.entity.dto;

import java.time.LocalDate;

public record ReviewDTO(
        Long id,
        Long movieId,
        String movieTitle,
        Long reviewRating,
        String reviewAuthor,
        String reviewText,
        LocalDate reviewDate,
        LocalDate reviewUpdateDate
) {
}
