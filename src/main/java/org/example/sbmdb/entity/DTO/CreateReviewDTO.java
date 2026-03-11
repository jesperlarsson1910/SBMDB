package org.example.sbmdb.entity.DTO;

import org.example.sbmdb.entity.Movie;

import java.time.LocalDate;

public record CreateReviewDTO(
        Movie movie,
        Long reviewRating,
        String reviewAuthor,
        String reviewText,
        LocalDate reviewDate
) {
}
