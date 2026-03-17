package org.example.sbmdb.controller;

import jakarta.validation.Valid;
import org.example.sbmdb.entity.dto.*;
import org.example.sbmdb.filter.ReviewFilter;
import org.example.sbmdb.service.ReviewService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateReviewDTO dto) {
        reviewService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewDTO(id));
    }

    @GetMapping
    public ResponseEntity<Page<ReviewDTO>> getReviews(
            @ParameterObject ReviewFilter filter,
            @ParameterObject @PageableDefault(size = 20, sort = "reviewDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(reviewService.search(filter, pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody UpdateReviewDTO dto) {
        reviewService.update(new UpdateReviewDTO(
                id,
                dto.reviewRating(),
                dto.reviewAuthor(),
                dto.reviewText()
        ));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}