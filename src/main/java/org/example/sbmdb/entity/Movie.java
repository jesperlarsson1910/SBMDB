package org.example.sbmdb.entity;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ElementCollection
    @CollectionTable(name = "movie_directors", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "director")
    private List<String> directors;

    private String description;
    private Long runningTime;
    private LocalDate releaseYear;
    private Double rating;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private final List<Review> reviews = new ArrayList<>();

    protected Movie() {}

    public Movie(String title, List<String> directors, String description, Long runningTime, LocalDate releaseYear) {
        this.title = title;
        this.directors = directors;
        this.description = description;
        this.runningTime = runningTime;
        this.releaseYear = releaseYear;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public String getDescription() {
        return description;
    }

    public Long getRunningTime() {
        return runningTime;
    }

    public LocalDate getReleaseYear() {
        return releaseYear;
    }

    public Double getRating() {
        return rating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRunningTime(Long runningTime) {
        this.runningTime = runningTime;
    }

    public void setReleaseYear(LocalDate releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void updateRating() {
        this.rating = reviews.stream()
                .mapToLong(Review::getReviewRating)
                .average()
                .orElse(0.0);
    }

}
