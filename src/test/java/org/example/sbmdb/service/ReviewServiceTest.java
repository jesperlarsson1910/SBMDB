package org.example.sbmdb.service;

import org.example.sbmdb.entity.dto.CreateReviewDTO;
import org.example.sbmdb.entity.dto.ReviewDTO;
import org.example.sbmdb.entity.dto.UpdateReviewDTO;
import org.example.sbmdb.entity.Movie;
import org.example.sbmdb.entity.Review;
import org.example.sbmdb.error.DuplicateEntityException;
import org.example.sbmdb.error.EntityNotFoundException;
import org.example.sbmdb.filter.ReviewFilter;
import org.example.sbmdb.repository.ReviewRepo;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepo reviewRepo;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private ReviewService reviewService;

    private Movie movie;
    private Review review;
    private CreateReviewDTO createDTO;
    private UpdateReviewDTO updateDTO;

    @BeforeEach
    void setUp() {
        movie = new Movie(
                "Inception",
                List.of("Christopher Nolan"),
                "A mind-bending thriller",
                148L,
                LocalDate.of(2010, 7, 16)
        );

        review = new Review(movie, 5.0, "John", "Great movie!");
        createDTO = new CreateReviewDTO(1L, 5.0, "John", "Great movie!");
        updateDTO = new UpdateReviewDTO(1L, 4.0, "John", Optional.of("Pretty good!"));
    }

    // --- create ---

    @Test
    void create_savesReview_whenUnique() {
        when(movieService.getMovie(1L)).thenReturn(movie);
        when(reviewRepo.isUnique(any())).thenReturn(true);

        reviewService.create(createDTO);

        verify(reviewRepo, times(1)).save(any(Review.class));
        verify(movieService, times(1)).updateMovieRating(any());
    }

    @Test
    void create_throwsDuplicateEntityException_whenNotUnique() {
        when(movieService.getMovie(1L)).thenReturn(movie);
        when(reviewRepo.isUnique(any())).thenReturn(false);

        assertThrows(DuplicateEntityException.class, () -> reviewService.create(createDTO));
        verify(reviewRepo, never()).save(any());
        verify(movieService, never()).updateMovieRating(any());
    }

    // --- getReview ---

    @Test
    void getReview_returnsReview_whenExists() {
        when(reviewRepo.findById(1L)).thenReturn(Optional.of(review));

        Review result = reviewService.getReview(1L);

        assertEquals("John", result.getReviewAuthor());
    }

    @Test
    void getReview_throwsEntityNotFoundException_whenNotFound() {
        when(reviewRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reviewService.getReview(1L));
    }

    // --- getReviewDTO ---

    @Test
    void getReviewDTO_returnsReviewDTO_whenExists() {
        when(reviewRepo.findById(1L)).thenReturn(Optional.of(review));

        ReviewDTO result = reviewService.getReviewDTO(1L);

        assertEquals("John", result.reviewAuthor());
    }

    // --- update ---

    @Test
    void update_updatesReview_whenExists() {
        when(reviewRepo.findById(1L)).thenReturn(Optional.of(review));

        reviewService.update(updateDTO);

        verify(reviewRepo, times(1)).save(review);
        verify(movieService, times(1)).updateMovieRating(any());
        assertEquals(4L, review.getReviewRating());
    }

    @Test
    void update_throwsEntityNotFoundException_whenNotFound() {
        when(reviewRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reviewService.update(updateDTO));
        verify(reviewRepo, never()).save(any());
    }

    // --- delete ---

    @Test
    void delete_deletesReview_whenExists() {
        when(reviewRepo.findById(1L)).thenReturn(Optional.of(review));

        reviewService.delete(1L);

        verify(reviewRepo, times(1)).delete(review);
        verify(movieService, times(1)).updateMovieRating(any());
    }

    @Test
    void delete_throwsEntityNotFoundException_whenNotFound() {
        when(reviewRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reviewService.delete(1L));
        verify(reviewRepo, never()).delete((Review) any());
    }

    // --- search ---

    @Test
    void search_returnsPage_whenFilterProvided() {
        ReviewFilter filter = new ReviewFilter(1L, null, null, null);
        Page<Review> page = new PageImpl<>(List.of(review));
        when(reviewRepo.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<ReviewDTO> result = reviewService.search(filter, Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals("John", result.getContent().getFirst().reviewAuthor());
    }
}
