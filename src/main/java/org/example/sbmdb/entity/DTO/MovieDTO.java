package org.example.sbmdb.entity.DTO;

import java.time.LocalDate;
import java.util.List;

public record MovieDTO(
        String title,
        List<String> directors,
        String description,
        Long runningTime,
        LocalDate releaseYear,
        Long rating,
        List<ReviewDTO> reviews
) {
}
