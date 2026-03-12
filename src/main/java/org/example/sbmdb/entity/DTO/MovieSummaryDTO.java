package org.example.sbmdb.entity.DTO;

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
