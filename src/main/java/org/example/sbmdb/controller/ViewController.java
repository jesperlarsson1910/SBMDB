package org.example.sbmdb.controller;

import org.example.sbmdb.entity.dto.*;
import org.example.sbmdb.filter.MovieFilter;
import org.example.sbmdb.filter.ReviewFilter;
import org.example.sbmdb.service.MovieService;
import org.example.sbmdb.service.ReviewService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ViewController {

    private final MovieService movieService;
    private final ReviewService reviewService;

    public ViewController(MovieService movieService, ReviewService reviewService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
    }

    // --- MOVIE VIEWS ---

    @GetMapping("/")
    public String movies(MovieFilter filter,
                         @PageableDefault(size = 20, sort = "title", direction = Sort.Direction.ASC) Pageable pageable,
                         Model model) {
        model.addAttribute("movies", movieService.search(filter, pageable));
        model.addAttribute("filter", filter);
        return "movies";
    }

    @GetMapping("/movies/{id}")
    public String movie(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieDTO(id));
        return "movie";
    }

    @GetMapping("/movies/create")
    public String createMovieForm() {
        return "movie-form";
    }

    @GetMapping("/movies/{id}/edit")
    public String editMovieForm(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieDTO(id));
        return "movie-form";
    }

    @PostMapping("/movies/create")
    public String createMovie(
            @RequestParam String title,
            @RequestParam String directors,
            @RequestParam(required = false) String description,
            @RequestParam Long runningTime,
            @RequestParam String releaseYear) {
        List<String> directorList = Arrays.stream(directors.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
        movieService.create(new CreateMovieDTO(
                title,
                directorList,
                description,
                runningTime,
                LocalDate.parse(releaseYear)
        ));
        return "redirect:/";
    }

    @PostMapping("/movies/{id}/edit")
    public String updateMovie(
            @PathVariable Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String directors,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long runningTime,
            @RequestParam(required = false) String releaseYear) {
        List<String> directorList = directors != null ? Arrays.stream(directors.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList()) : null;
        movieService.update(new UpdateMovieDTO(
                id,
                title,
                directorList,
                description != null ? Optional.of(description) : null,
                runningTime,
                releaseYear != null ? LocalDate.parse(releaseYear) : null
        ));
        return "redirect:/movies/" + id;
    }

    @PostMapping("/movies/{id}/delete")
    public String deleteMovie(@PathVariable Long id) {
        movieService.delete(id);
        return "redirect:/";
    }

    // --- REVIEW VIEWS ---

    @GetMapping("/reviews")
    public String reviews(ReviewFilter filter,
                          @PageableDefault(size = 20, sort = "reviewDate", direction = Sort.Direction.DESC) Pageable pageable,
                          Model model) {
        model.addAttribute("reviews", reviewService.search(filter, pageable));
        model.addAttribute("filter", filter);
        return "reviews";
    }

    @GetMapping("/reviews/{id}")
    public String review(@PathVariable Long id, Model model) {
        model.addAttribute("review", reviewService.getReviewDTO(id));
        return "review";
    }

    @GetMapping("/movies/{id}/review")
    public String createReviewForm(@PathVariable Long id, Model model) {
        model.addAttribute("movieId", id);
        return "review-form";
    }

    @GetMapping("/reviews/{id}/edit")
    public String editReviewForm(@PathVariable Long id, Model model) {
        ReviewDTO review = reviewService.getReviewDTO(id);
        model.addAttribute("movieId", review.movieId());
        model.addAttribute("review", review);
        return "review-form";
    }

    @PostMapping("/reviews/create")
    public String createReview(
            @RequestParam Long movieId,
            @RequestParam Long reviewRating,
            @RequestParam String reviewAuthor,
            @RequestParam(required = false) String reviewText) {
        reviewService.create(new CreateReviewDTO(
                movieId,
                reviewRating,
                reviewAuthor,
                reviewText
        ));
        return "redirect:/movies/" + movieId;
    }

    @PostMapping("/reviews/{id}/edit")
    public String updateReview(
            @PathVariable Long id,
            @RequestParam Long movieId,
            @RequestParam(required = false) Long reviewRating,
            @RequestParam(required = false) String reviewAuthor,
            @RequestParam(required = false) String reviewText) {
        reviewService.update(new UpdateReviewDTO(
                id,
                reviewRating,
                reviewAuthor,
                reviewText != null ? Optional.of(reviewText) : null
        ));
        return "redirect:/movies/" + movieId;
    }

    @PostMapping("/reviews/{id}/delete")
    public String deleteReview(@PathVariable Long id) {
        ReviewDTO review = reviewService.getReviewDTO(id);
        Long movieId = review.movieId();
        reviewService.delete(id);
        return "redirect:/movies/" + movieId;
    }
}