package org.example.sbmdb.filter;

import java.time.LocalDate;

public record MovieFilter(
        String title,
        String director,
        String description,
        Long runningTimeLow,
        Long runningTimeHigh,
        LocalDate releaseFrom,
        LocalDate releaseTo,
        Double ratingLow,
        Double ratingHigh
) {
}
