package org.example.sbmdb.entity.DTO;

import java.time.LocalDate;

public record UpdateReviewDTO(
        Long id,
        Long reviewRating,
        String reviewAuthor,
        LocalDate reviewUpdateDate
) {
}
