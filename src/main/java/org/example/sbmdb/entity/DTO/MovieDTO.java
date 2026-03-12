package org.example.sbmdb.entity.DTO;

import java.time.LocalDate;
import java.util.List;

public record MovieDTO(
        Long id,
        String title,
        List<String> directors,
        String description,
        Long runningTime,
        LocalDate releaseYear,
        Double rating,
        List<ReviewDTO> reviews
) {
}
