package org.example.sbmdb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.sbmdb.entity.dto.CreateMovieDTO;
import org.example.sbmdb.entity.dto.MovieDTO;
import org.example.sbmdb.entity.dto.MovieSummaryDTO;
import org.example.sbmdb.entity.dto.UpdateMovieDTO;
import org.example.sbmdb.error.DuplicateEntityException;
import org.example.sbmdb.error.EntityNotFoundException;
import org.example.sbmdb.filter.MovieFilter;
import org.example.sbmdb.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    private ObjectMapper objectMapper;
    private MovieDTO movieDTO;
    private MovieSummaryDTO movieSummaryDTO;
    private CreateMovieDTO createDTO;
    private UpdateMovieDTO updateDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        movieDTO = new MovieDTO(
                1L,
                "Inception",
                List.of("Christopher Nolan"),
                "A mind-bending thriller",
                148L,
                LocalDate.of(2010, 7, 16),
                4.5,
                List.of()
        );

        movieSummaryDTO = new MovieSummaryDTO(
                1L,
                "Inception",
                List.of("Christopher Nolan"),
                LocalDate.of(2010, 7, 16),
                4.5
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
                null,
                160L,
                LocalDate.of(2010, 7, 16)
        );
    }

    // --- GET /api/movies ---

    @Test
    void getMovies_returnsPage() throws Exception {
        when(movieService.search(any(MovieFilter.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(movieSummaryDTO)));

        mockMvc.perform(get("/api/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Inception"));
    }

    // --- GET /api/movies/{id} ---

    @Test
    void getMovie_returnsMovie_whenExists() throws Exception {
        when(movieService.getMovieDTO(1L)).thenReturn(movieDTO);

        mockMvc.perform(get("/api/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception"));
    }

    @Test
    void getMovie_returns404_whenNotFound() throws Exception {
        when(movieService.getMovieDTO(1L)).thenThrow(new EntityNotFoundException("Movie", 1L));

        mockMvc.perform(get("/api/movies/1"))
                .andExpect(status().isNotFound());
    }

    // --- POST /api/movies ---

    @Test
    void create_returns201_whenValid() throws Exception {
        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated());

        verify(movieService, times(1)).create(any(CreateMovieDTO.class));
    }

    @Test
    void create_returns409_whenDuplicate() throws Exception {
        doThrow(new DuplicateEntityException("Movie")).when(movieService).create(any());

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void create_returns400_whenInvalidBody() throws Exception {
        CreateMovieDTO invalidDTO = new CreateMovieDTO(null, null, null, null, null);

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

    // --- PATCH /api/movies/{id} ---

    @Test
    void update_returns204_whenValid() throws Exception {
        mockMvc.perform(patch("/api/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNoContent());

        verify(movieService, times(1)).update(any(UpdateMovieDTO.class));
    }

    @Test
    void update_returns404_whenNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Movie", 1L)).when(movieService).update(any());

        mockMvc.perform(patch("/api/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    // --- DELETE /api/movies/{id} ---

    @Test
    void delete_returns204_whenExists() throws Exception {
        mockMvc.perform(delete("/api/movies/1"))
                .andExpect(status().isNoContent());

        verify(movieService, times(1)).delete(1L);
    }

    @Test
    void delete_returns404_whenNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Movie", 1L)).when(movieService).delete(1L);

        mockMvc.perform(delete("/api/movies/1"))
                .andExpect(status().isNotFound());
    }
}