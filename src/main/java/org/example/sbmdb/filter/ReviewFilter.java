package org.example.sbmdb.filter;

import org.example.sbmdb.entity.Movie;

public record ReviewFilter(
        Movie movie,
        Long ratingLow,
        Long ratingHigh,
        String author
) {

}
