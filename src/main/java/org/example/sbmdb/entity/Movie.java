package org.example.sbmdb.entity;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.ArrayList;
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
    private LocalDate releaseYear;
    private Long rating;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    protected Movie() {}

    public Long getId() {
        return id;
    }

    public Long getRating() {
        return rating;
    }

    public LocalDate getReleaseYear() {
        return releaseYear;
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
