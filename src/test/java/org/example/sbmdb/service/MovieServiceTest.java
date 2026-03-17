package org.example.sbmdb.service;

import org.example.sbmdb.entity.dto.CreateMovieDTO;
import org.example.sbmdb.entity.dto.MovieDTO;
import org.example.sbmdb.entity.dto.MovieSummaryDTO;
import org.example.sbmdb.entity.dto.UpdateMovieDTO;
import org.example.sbmdb.entity.Movie;
import org.example.sbmdb.error.DuplicateEntityException;
import org.example.sbmdb.error.EntityNotFoundException;
import org.example.sbmdb.filter.MovieFilter;
import org.example.sbmdb.repository.MovieRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepo movieRepo;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;
    private CreateMovieDTO createDTO;
    private UpdateMovieDTO updateDTO;

    @BeforeEach
    void setUp() {
        movie = new Movie(
                "Inception",
                List.of("Christopher Nolan"),
                "A mind-bending thriller",
                148L,
                LocalDate.of(2010, 7, 16)
        );

        createDTO = new CreateMovieDTO(
                "Inception",
                List.of("Christopher Nolan"),
                "A mind-bending thriller",
                148L,
                LocalDate.of(2010, 7, 16)
        );

        updateDTO = new UpdateMovieDTO(
                1L,
                "Inception Updated",
                List.of("Christopher Nolan"),
                Optional.of("Updated description"),
                160L,
                LocalDate.of(2010, 7, 16)
        );
    }

    // --- create ---

    @Test
    void create_savesMovie_whenUnique() {
        when(movieRepo.isUnique(any())).thenReturn(true);

        movieService.create(createDTO);

        verify(movieRepo, times(1)).save(any(Movie.class));
    }

    @Test
    void create_throwsDuplicateEntityException_whenNotUnique() {
        when(movieRepo.isUnique(any())).thenReturn(false);

        assertThrows(DuplicateEntityException.class, () -> movieService.create(createDTO));
        verify(movieRepo, never()).save(any());
    }

    // --- getMovie ---

    @Test
    void getMovie_returnsMovie_whenExists() {
        when(movieRepo.findById(1L)).thenReturn(Optional.of(movie));

        Movie result = movieService.getMovie(1L);

        assertEquals("Inception", result.getTitle());
    }

    @Test
    void getMovie_throwsEntityNotFoundException_whenNotFound() {
        when(movieRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> movieService.getMovie(1L));
    }

    // --- getMovieDTO ---

    @Test
    void getMovieDTO_returnsMovieDTO_whenExists() {
        when(movieRepo.findById(1L)).thenReturn(Optional.of(movie));

        MovieDTO result = movieService.getMovieDTO(1L);

        assertEquals("Inception", result.title());
    }

    // --- update ---

    @Test
    void update_updatesMovie_whenExists() {
        when(movieRepo.findById(1L)).thenReturn(Optional.of(movie));

        movieService.update(updateDTO);

        verify(movieRepo, times(1)).save(movie);
        assertEquals("Inception Updated", movie.getTitle());
    }

    @Test
    void update_throwsEntityNotFoundException_whenNotFound() {
        when(movieRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> movieService.update(updateDTO));
        verify(movieRepo, never()).save(any());
    }

    // --- delete ---

    @Test
    void delete_deletesMovie_whenExists() {
        when(movieRepo.findById(1L)).thenReturn(Optional.of(movie));

        movieService.delete(1L);

        verify(movieRepo, times(1)).delete(movie);
    }

    @Test
    void delete_throwsEntityNotFoundException_whenNotFound() {
        when(movieRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> movieService.delete(1L));
        verify(movieRepo, never()).delete((Movie) any());
    }

    // --- updateMovieRating ---

    @Test
    void updateMovieRating_updatesRating_whenExists() {
        when(movieRepo.findById(1L)).thenReturn(Optional.of(movie));

        movieService.updateMovieRating(1L);

        verify(movieRepo, times(1)).save(movie);
    }

    // --- search ---

    @Test
    void search_returnsPage_whenFilterProvided() {
        MovieFilter filter = new MovieFilter("Inception", null, null, null, null, null, null, null, null);
        Page<Movie> page = new PageImpl<>(List.of(movie));
        when(movieRepo.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<MovieSummaryDTO> result = movieService.search(filter, Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("Inception", result.getContent().getFirst().title());
    }

    @Test
    void search_returnsEmptyPage_whenNoMatches() {
        MovieFilter filter = new MovieFilter("Unknown", null, null, null, null, null, null, null, null);
        when(movieRepo.findAll(any(Specification.class), any(Pageable.class))).thenReturn(Page.empty());

        Page<MovieSummaryDTO> result = movieService.search(filter, Pageable.unpaged());

        assertTrue(result.isEmpty());
    }

    // --- getMovies ---

    @Test
    void getMovies_returnsPage() {
        Page<Movie> page = new PageImpl<>(List.of(movie));
        when(movieRepo.findAll(any(Pageable.class))).thenReturn(page);

        Page<MovieSummaryDTO> result = movieService.getMovies(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
    }
}
