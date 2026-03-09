package org.example.sbmdb.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    private Long reviewRating;

    private String reviewAuthor;

    private String reviewText;

    private LocalDate reviewDate;

    public Long getId() {
        return id;
    }

    public Long getReviewRating() {
        return reviewRating;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public String getReviewText() {
        return reviewText;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewRating(Long reviewRating) {
        this.reviewRating = reviewRating;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
}
