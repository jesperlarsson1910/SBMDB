package org.example.sbmdb.entity.mapper;

import org.example.sbmdb.entity.DTO.CreateReviewDTO;
import org.example.sbmdb.entity.DTO.ReviewDTO;
import org.example.sbmdb.entity.DTO.UpdateReviewDTO;
import org.example.sbmdb.entity.Movie;
import org.example.sbmdb.entity.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReviewMapperTest {

    private Movie movie;
    private Review review;

    @BeforeEach
    void setUp() {
        movie = new Movie(
                "Inception",
                List.of("Christopher Nolan"),
                "A mind-bending thriller",
                148L,
                LocalDate.of(2010, 7, 16)
        );
        review = new Review(movie, 5L, "John", "Great movie!");
    }

    @Test
    void createReview_mapsAllFields() {
        CreateReviewDTO dto = new CreateReviewDTO(1L, 5L, "John", "Great movie!");

        Review result = ReviewMapper.createReview(movie, dto);

        assertEquals(movie, result.getMovie());
        assertEquals(5L, result.getReviewRating());
        assertEquals("John", result.getReviewAuthor());
        assertEquals("Great movie!", result.getReviewText());
        assertEquals(LocalDate.now(), result.getReviewDate());
    }

    @Test
    void createReview_setsNullText_whenNotProvided() {
        CreateReviewDTO dto = new CreateReviewDTO(1L, 5L, "John", null);

        Review result = ReviewMapper.createReview(movie, dto);

        assertNull(result.getReviewText());
    }

    @Test
    void updateReview_updatesAllFields_whenAllProvided() {
        UpdateReviewDTO dto = new UpdateReviewDTO(1L, 4L, "Jane", Optional.of("Pretty good!"));

        ReviewMapper.updateReview(review, dto);

        assertEquals(4L, review.getReviewRating());
        assertEquals("Jane", review.getReviewAuthor());
        assertEquals("Pretty good!", review.getReviewText());
        assertEquals(LocalDate.now(), review.getReviewUpdateDate());
    }

    @Test
    void updateReview_doesNotOverwrite_whenFieldsAreNull() {
        UpdateReviewDTO dto = new UpdateReviewDTO(1L, null, null, null);

        ReviewMapper.updateReview(review, dto);

        assertEquals(5L, review.getReviewRating());
        assertEquals("John", review.getReviewAuthor());
        assertEquals("Great movie!", review.getReviewText());
    }

    @Test
    void updateReview_clearsText_whenOptionalEmpty() {
        UpdateReviewDTO dto = new UpdateReviewDTO(1L, null, null, Optional.empty());

        ReviewMapper.updateReview(review, dto);

        assertNull(review.getReviewText());
    }

    @Test
    void createReviewDTO_mapsAllFields() {
        ReviewDTO result = ReviewMapper.createReviewDTO(review);

        assertEquals("John", result.reviewAuthor());
        assertEquals(5L, result.reviewRating());
        assertEquals("Great movie!", result.reviewText());
        assertNotNull(result.reviewDate());
    }
}
