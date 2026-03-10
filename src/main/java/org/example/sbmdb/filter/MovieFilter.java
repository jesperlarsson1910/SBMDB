package org.example.sbmdb.filter;

import java.time.LocalDate;

public record MovieFilter(
        String title,
        String director,
        String description,
        Long runningTimeLow,
        Long runningTimeHigh,
        LocalDate releaseBefore,
        LocalDate releaseAfter,
        Long ratingLow,
        Long ratingHigh
) {
}
