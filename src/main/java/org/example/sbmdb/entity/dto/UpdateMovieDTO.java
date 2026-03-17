package org.example.sbmdb.entity.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record UpdateMovieDTO(
        Long id,
        String title,
        List<String> directors,
        Optional<String> description,
        Long runningTime,
        LocalDate releaseYear
) {
}
