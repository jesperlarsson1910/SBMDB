package org.example.sbmdb.service;

import org.example.sbmdb.entity.DTO.CreateReviewDTO;
import org.example.sbmdb.entity.DTO.ReviewDTO;
import org.example.sbmdb.entity.DTO.UpdateReviewDTO;
import org.example.sbmdb.entity.Review;
import org.example.sbmdb.entity.mapper.ReviewMapper;
import org.example.sbmdb.error.*;
import org.example.sbmdb.repository.ReviewRepo;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepo reviewRepo;

    public ReviewService(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    public void create(CreateReviewDTO dto) {
        Review review = ReviewMapper.createReview(dto);

        if(!reviewRepo.isUnique(review)){
            throw new DuplicateEntityException("Review");
        }

        reviewRepo.save(review);
    }

    public Review getReview(long id) {
        return reviewRepo.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Review", id));
    }

    public ReviewDTO getReviewDTO(long id) {
        return ReviewMapper.createReviewDTO(getReview(id));
    }

    public void update(UpdateReviewDTO dto) {
        Review review = getReview(dto.id());

        ReviewMapper.updateReview(review, dto);
        reviewRepo.save(review);
    }

    public void delete(long id) {
        Review review = getReview(id);

        reviewRepo.delete(review);
    }
}
