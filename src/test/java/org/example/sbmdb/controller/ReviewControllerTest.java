package org.example.sbmdb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.sbmdb.entity.dto.CreateReviewDTO;
import org.example.sbmdb.entity.dto.ReviewDTO;
import org.example.sbmdb.entity.dto.UpdateReviewDTO;
import org.example.sbmdb.error.DuplicateEntityException;
import org.example.sbmdb.error.EntityNotFoundException;
import org.example.sbmdb.filter.ReviewFilter;
import org.example.sbmdb.service.ReviewService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReviewService reviewService;

    private ObjectMapper objectMapper;
    private ReviewDTO reviewDTO;
    private CreateReviewDTO createDTO;
    private UpdateReviewDTO updateDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

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

        createDTO = new CreateReviewDTO(1L, 5.0, "John", "Great movie!");
        updateDTO = new UpdateReviewDTO(1L, 4.0, "John", null);
    }

    // --- GET /api/reviews ---

    @Test
    void getReviews_returnsPage() throws Exception {
        when(reviewService.search(any(ReviewFilter.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(reviewDTO)));

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].reviewAuthor").value("John"));
    }

    // --- GET /api/reviews/{id} ---

    @Test
    void getReview_returnsReview_whenExists() throws Exception {
        when(reviewService.getReviewDTO(1L)).thenReturn(reviewDTO);

        mockMvc.perform(get("/api/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewAuthor").value("John"));
    }

    @Test
    void getReview_returns404_whenNotFound() throws Exception {
        when(reviewService.getReviewDTO(1L)).thenThrow(new EntityNotFoundException("Review", 1L));

        mockMvc.perform(get("/api/reviews/1"))
                .andExpect(status().isNotFound());
    }

    // --- POST /api/reviews ---

    @Test
    void create_returns201_whenValid() throws Exception {
        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated());

        verify(reviewService, times(1)).create(any(CreateReviewDTO.class));
    }

    @Test
    void create_returns409_whenDuplicate() throws Exception {
        doThrow(new DuplicateEntityException("Review")).when(reviewService).create(any());

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void create_returns400_whenInvalidBody() throws Exception {
        CreateReviewDTO invalidDTO = new CreateReviewDTO(null, null, null, null);

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

    // --- PATCH /api/reviews/{id} ---

    @Test
    void update_returns204_whenValid() throws Exception {
        mockMvc.perform(patch("/api/reviews/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNoContent());

        verify(reviewService, times(1)).update(any(UpdateReviewDTO.class));
    }

    @Test
    void update_returns404_whenNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Review", 1L)).when(reviewService).update(any());

        mockMvc.perform(patch("/api/reviews/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    // --- DELETE /api/reviews/{id} ---

    @Test
    void delete_returns204_whenExists() throws Exception {
        mockMvc.perform(delete("/api/reviews/1"))
                .andExpect(status().isNoContent());

        verify(reviewService, times(1)).delete(1L);
    }

    @Test
    void delete_returns404_whenNotFound() throws Exception {
        doThrow(new EntityNotFoundException("Review", 1L)).when(reviewService).delete(1L);

        mockMvc.perform(delete("/api/reviews/1"))
                .andExpect(status().isNotFound());
    }
}