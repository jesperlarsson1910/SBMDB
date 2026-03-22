package org.example.sbmdb.controller;

import org.example.sbmdb.entity.dto.MovieDTO;
import org.example.sbmdb.entity.dto.MovieSummaryDTO;
import org.example.sbmdb.entity.dto.ReviewDTO;
import org.example.sbmdb.filter.MovieFilter;
import org.example.sbmdb.filter.ReviewFilter;
import org.example.sbmdb.service.MovieService;
import org.example.sbmdb.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    @MockitoBean
    private ReviewService reviewService;

    private MovieDTO movieDTO;
    private MovieSummaryDTO movieSummaryDTO;
    private ReviewDTO reviewDTO;

    @BeforeEach
    void setUp() {
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

        reviewDTO = new ReviewDTO(
                1L,
                1L,
                "Inception",
                5.0,
                "John",
                "Great movie!",
                LocalDate.now(),
                LocalDate.now()
        );
    }

    // --- GET / ---

    @Test
    void movies_returnsMoviesView() throws Exception {
        when(movieService.search(any(MovieFilter.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(movieSummaryDTO)));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("movies"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attributeExists("filter"))
                .andExpect(model().attributeExists("currentSort"));
    }

    // --- GET /movies/{id} ---

    @Test
    void movie_returnsMovieView() throws Exception {
        when(movieService.getMovieDTO(any(Long.class), any(Sort.class))).thenReturn(movieDTO);

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("movie"))
                .andExpect(model().attributeExists("movie"))
                .andExpect(model().attributeExists("currentSort"));
    }

    // --- GET /movies/create ---

    @Test
    void createMovieForm_returnsMovieFormView() throws Exception {
        mockMvc.perform(get("/movies/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("movie-form"));
    }

    // --- GET /movies/{id}/edit ---

    @Test
    void editMovieForm_returnsMovieFormView() throws Exception {
        when(movieService.getMovieDTO(1L)).thenReturn(movieDTO);

        mockMvc.perform(get("/movies/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("movie-form"))
                .andExpect(model().attributeExists("movie"));
    }

    // --- GET /reviews ---

    @Test
    void reviews_returnsReviewsView() throws Exception {
        when(reviewService.search(any(ReviewFilter.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(reviewDTO)));

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("reviews"))
                .andExpect(model().attributeExists("filter"))
                .andExpect(model().attributeExists("currentSort"));
    }

    // --- GET /reviews/{id} ---

    @Test
    void review_returnsReviewView() throws Exception {
        when(reviewService.getReviewDTO(1L)).thenReturn(reviewDTO);

        mockMvc.perform(get("/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("review"))
                .andExpect(model().attributeExists("review"));
    }

    // --- GET /movies/{id}/review ---

    @Test
    void createReviewForm_returnsReviewFormView() throws Exception {
        mockMvc.perform(get("/movies/1/review"))
                .andExpect(status().isOk())
                .andExpect(view().name("review-form"))
                .andExpect(model().attributeExists("movieId"));
    }

    // --- GET /reviews/{id}/edit ---

    @Test
    void editReviewForm_returnsReviewFormView() throws Exception {
        when(reviewService.getReviewDTO(1L)).thenReturn(reviewDTO);

        mockMvc.perform(get("/reviews/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("review-form"))
                .andExpect(model().attributeExists("review"))
                .andExpect(model().attributeExists("movieId"));
    }

    // --- POST /movies/create ---

    @Test
    void createMovie_redirectsToHome_whenValid() throws Exception {
        mockMvc.perform(post("/movies/create")
                        .param("title", "Inception")
                        .param("directors", "Christopher Nolan")
                        .param("runningTime", "148")
                        .param("releaseYear", "2010-07-16"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void createMovie_returnsFormWithErrors_whenInvalid() throws Exception {
        mockMvc.perform(post("/movies/create")
                        .param("title", "")
                        .param("directors", "")
                        .param("runningTime", "")
                        .param("releaseYear", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("movie-form"))
                .andExpect(model().attributeExists("errors"));
    }

    // --- POST /reviews/create ---

    @Test
    void createReview_redirectsToMovie_whenValid() throws Exception {
        mockMvc.perform(post("/reviews/create")
                        .param("movieId", "1")
                        .param("reviewRating", "5.0")
                        .param("reviewAuthor", "John")
                        .param("reviewText", "Great movie!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movies/1"));
    }

    @Test
    void createReview_returnsFormWithErrors_whenInvalid() throws Exception {
        mockMvc.perform(post("/reviews/create")
                        .param("movieId", "1")
                        .param("reviewRating", "")
                        .param("reviewAuthor", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("review-form"))
                .andExpect(model().attributeExists("errors"));
    }

    // --- POST /movies/{id}/delete ---

    @Test
    void deleteMovie_redirectsToHome() throws Exception {
        mockMvc.perform(post("/movies/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    // --- POST /reviews/{id}/delete ---

    @Test
    void deleteReview_redirectsToMovie() throws Exception {
        when(reviewService.getReviewDTO(1L)).thenReturn(reviewDTO);

        mockMvc.perform(post("/reviews/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/movies/1"));
    }
}