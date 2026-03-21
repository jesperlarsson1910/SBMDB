package org.example.sbmdb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @NotNull
    @DecimalMin("0.5")
    @DecimalMax("5.0")
    private Double reviewRating;

    @NotBlank
    private String reviewAuthor;

    private String reviewText;

    private LocalDate reviewDate;
    private LocalDate reviewUpdateDate;

    protected Review() {}

    public Review(Movie movie, Double reviewRating, String reviewAuthor, String reviewText) {
        this.movie = movie;
        this.reviewRating = reviewRating;
        this.reviewAuthor = reviewAuthor;
        this.reviewText = reviewText;
        this.reviewDate = LocalDate.now();
        this.reviewUpdateDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Double getReviewRating() {
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

    public LocalDate getReviewUpdateDate() {
        return reviewUpdateDate;
    }

    public void setReviewRating(Double reviewRating) {
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

    public void setReviewUpdateDate(LocalDate reviewUpdateDate) {
        this.reviewUpdateDate = reviewUpdateDate;
    }

}
