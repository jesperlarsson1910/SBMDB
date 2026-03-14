package org.example.sbmdb.filter;

public record ReviewFilter(
        Long movieId,
        Double ratingLow,
        Double ratingHigh,
        String author
) {
}
