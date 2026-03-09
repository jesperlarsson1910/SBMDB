package org.example.sbmdb.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String director;

    private String description;

    private Long runningTime;

    private LocalDate releaseDate;

    private Long rating;

    private List<Review> reviews;

    public Long getId() {
        return id;
    }

    public Long getRating() {
        return rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Long getRunningTime() {
        return runningTime;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public String getTitle() {
        return title;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }
}
