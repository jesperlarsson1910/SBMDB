package org.example.sbmdb.entity.mapper;

import org.example.sbmdb.entity.dto.*;
import org.example.sbmdb.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MovieMapperTest {

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie(
                "Inception",
                List.of("Christopher Nolan"),
                "A mind-bending thriller",
                148L,
                LocalDate.of(2010, 7, 16)
        );
    }

    @Test
    void createMovie_mapsAllFields() {
        CreateMovieDTO dto = new CreateMovieDTO(
                "Inception",
                List.of("Christopher Nolan"),
                "A mind-bending thriller",
                148L,
                LocalDate.of(2010, 7, 16)
        );

        Movie result = MovieMapper.createMovie(dto);

        assertEquals("Inception", result.getTitle());
        assertEquals(List.of("Christopher Nolan"), result.getDirectors());
        assertEquals("A mind-bending thriller", result.getDescription());
        assertEquals(148L, result.getRunningTime());
        assertEquals(LocalDate.of(2010, 7, 16), result.getReleaseYear());
    }

    @Test
    void createMovie_setsNullDescription_whenNotProvided() {
        CreateMovieDTO dto = new CreateMovieDTO(
                "Inception",
                List.of("Christopher Nolan"),
                null,
                148L,
                LocalDate.of(2010, 7, 16)
        );

        Movie result = MovieMapper.createMovie(dto);

        assertNull(result.getDescription());
    }

    @Test
    void updateMovie_updatesAllFields_whenAllProvided() {
        UpdateMovieDTO dto = new UpdateMovieDTO(
                1L,
                "Inception Updated",
                List.of("Nolan"),
                Optional.of("New description"),
                160L,
                LocalDate.of(2011, 1, 1)
        );

        MovieMapper.updateMovie(movie, dto);

        assertEquals("Inception Updated", movie.getTitle());
        assertEquals(List.of("Nolan"), movie.getDirectors());
        assertEquals("New description", movie.getDescription());
        assertEquals(160L, movie.getRunningTime());
        assertEquals(LocalDate.of(2011, 1, 1), movie.getReleaseYear());
    }

    @Test
    void updateMovie_doesNotOverwrite_whenFieldsAreNull() {
        UpdateMovieDTO dto = new UpdateMovieDTO(1L, null, null, null, null, null);

        MovieMapper.updateMovie(movie, dto);

        assertEquals("Inception", movie.getTitle());
        assertEquals(List.of("Christopher Nolan"), movie.getDirectors());
        assertEquals("A mind-bending thriller", movie.getDescription());
        assertEquals(148L, movie.getRunningTime());
    }

    @Test
    void updateMovie_clearsDescription_whenOptionalEmpty() {
        UpdateMovieDTO dto = new UpdateMovieDTO(1L, null, null, Optional.empty(), null, null);

        MovieMapper.updateMovie(movie, dto);

        assertNull(movie.getDescription());
    }

    @Test
    void createMovieDTO_mapsAllFields() {
        MovieDTO result = MovieMapper.createMovieDTO(movie);

        assertEquals("Inception", result.title());
        assertEquals(List.of("Christopher Nolan"), result.directors());
        assertEquals("A mind-bending thriller", result.description());
        assertEquals(148L, result.runningTime());
        assertEquals(LocalDate.of(2010, 7, 16), result.releaseYear());
        assertNotNull(result.reviews());
    }

    @Test
    void createMovieSummaryDTO_mapsAllFields() {
        MovieSummaryDTO result = MovieMapper.createMovieSummaryDTO(movie);

        assertEquals("Inception", result.title());
        assertEquals(List.of("Christopher Nolan"), result.directors());
        assertEquals(LocalDate.of(2010, 7, 16), result.releaseYear());
    }
}
