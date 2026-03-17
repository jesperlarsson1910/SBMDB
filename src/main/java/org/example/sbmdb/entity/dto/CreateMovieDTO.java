package org.example.sbmdb.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CreateMovieDTO(
        @NotBlank String title,
        @NotEmpty List<String> directors,
        String description,
        @NotNull Long runningTime,
        @NotNull LocalDate releaseYear
) {
}
