package org.example.sbmdb.entity.DTO;

import org.example.sbmdb.entity.Movie;

import java.time.LocalDate;

public record ReviewDTO(
        Movie movie,
        Long reviewRating,
        String reviewAuthor,
        String reviewText,
        LocalDate reviewDate,
        LocalDate reviewUpdateDate
) {
}
