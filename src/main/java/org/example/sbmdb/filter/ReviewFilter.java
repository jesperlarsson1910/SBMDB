package org.example.sbmdb.filter;

public record ReviewFilter(
        Long movieId,
        Double ratingMin,
        Double ratingMax,
        String author
) {
}
