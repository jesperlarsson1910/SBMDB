package org.example.sbmdb.entity.dto;

import java.time.LocalDate;
import java.util.List;

public record MovieSummaryDTO(
        Long id,
        String title,
        List<String> directors,
        LocalDate releaseYear,
        Double rating
) {
}
