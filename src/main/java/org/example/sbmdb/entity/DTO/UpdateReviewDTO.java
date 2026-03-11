package org.example.sbmdb.entity.DTO;

import java.time.LocalDate;

public record UpdateReviewDTO(
        Long reviewRating,
        String reviewAuthor,
        LocalDate reviewUpdateDate
) {
}
