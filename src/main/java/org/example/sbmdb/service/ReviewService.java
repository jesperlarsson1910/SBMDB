package org.example.sbmdb.service;

import org.example.sbmdb.entity.DTO.CreateReviewDTO;
import org.example.sbmdb.entity.DTO.ReviewDTO;
import org.example.sbmdb.entity.DTO.UpdateReviewDTO;
import org.example.sbmdb.entity.Movie;
import org.example.sbmdb.entity.Review;
import org.example.sbmdb.entity.mapper.ReviewMapper;
import org.example.sbmdb.error.*;
import org.example.sbmdb.repository.ReviewRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final ReviewRepo reviewRepo;
    private final MovieService movieService;

    public ReviewService(ReviewRepo reviewRepo,  MovieService movieService) {
        this.reviewRepo = reviewRepo;
        this.movieService = movieService;
    }

    public void create(CreateReviewDTO dto) {
        Movie movie = movieService.getMovie(dto.movieId());
        Review review = ReviewMapper.createReview(movie, dto);

        if(!reviewRepo.isUnique(review)){
            throw new DuplicateEntityException("Review");
        }

        reviewRepo.save(review);
        movieService.updateMovieRating(review.getMovie().getId());
    }

    public Review getReview(long id) {
        return reviewRepo.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Review", id));
    }

    public ReviewDTO getReviewDTO(long id) {
        return ReviewMapper.createReviewDTO(getReview(id));
    }

    @Transactional
    public void update(UpdateReviewDTO dto) {
        Review review = getReview(dto.id());

        ReviewMapper.updateReview(review, dto);
        reviewRepo.save(review);
        movieService.updateMovieRating(review.getMovie().getId());
    }

    @Transactional
    public void delete(long id) {
        Review review = getReview(id);

        reviewRepo.delete(review);
        movieService.updateMovieRating(review.getMovie().getId());
    }
}
