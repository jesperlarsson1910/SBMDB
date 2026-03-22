package org.example.sbmdb.filter;

public record MovieFilter(
        String title,
        String director,
        String description,
        Long runningTimeMin,
        Long runningTimeMax,
        Integer releaseYearFrom,
        Integer releaseYearTo,
        Double ratingMin,
        Double ratingMax
) {
}
