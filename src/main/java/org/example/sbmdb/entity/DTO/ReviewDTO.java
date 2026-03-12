package org.example.sbmdb.entity.DTO;

import org.example.sbmdb.entity.Movie;

import java.time.LocalDate;

public record ReviewDTO(
        Long id,
        Long movieId,
        Long reviewRating,
        String reviewAuthor,
        String reviewText,
        LocalDate reviewDate,
        LocalDate reviewUpdateDate
) {
}
