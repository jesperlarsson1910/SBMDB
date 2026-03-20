package org.example.sbmdb.controller;

import org.example.sbmdb.entity.dto.*;
import org.example.sbmdb.error.DuplicateEntityException;
import org.example.sbmdb.filter.MovieFilter;
import org.example.sbmdb.filter.ReviewFilter;
import org.example.sbmdb.service.MovieService;
import org.example.sbmdb.service.ReviewService;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String movies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long runningTimeMin,
            @RequestParam(required = false) Long runningTimeMax,
            @RequestParam(required = false) Integer releaseYearFrom,
            @RequestParam(required = false) Integer releaseYearTo,
            @RequestParam(required = false) Double ratingMin,
            @RequestParam(required = false) Double ratingMax,
            @PageableDefault(size = 20, sort = "title", direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {
        MovieFilter filter = new MovieFilter(title, director, description, runningTimeMin, runningTimeMax, releaseYearFrom, releaseYearTo, ratingMin, ratingMax);
        try {
            model.addAttribute("movies", movieService.search(filter, pageable));
        } catch (IllegalArgumentException e) {
            model.addAttribute("movies", Page.empty());
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("filter", filter);
        model.addAttribute("pageable", pageable);
        return "movies";
    }

    @GetMapping("/movies/{id}")
    public String movie(@PathVariable Long id,
                        @RequestParam(required = false, defaultValue = "reviewDate,desc") String sort,
                        Model model) {
        String[] sortParts = sort.split(",");
        Sort.Direction direction = sortParts.length > 1 && sortParts[1].equals("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        model.addAttribute("movie", movieService.getMovieDTO(id, Sort.by(direction, sortParts[0])));
        return "movie";
    }

    @GetMapping("/movies/create")
    public String createMovieForm(
            @ModelAttribute("error") String error,
            @ModelAttribute("formData_title") String title,
            @ModelAttribute("formData_directors") String directors,
            @ModelAttribute("formData_description") String description,
            @ModelAttribute("formData_runningTime") String runningTime,
            @ModelAttribute("formData_releaseYear") String releaseYear,
            Model model) {
        if (error != null && !error.isBlank()) model.addAttribute("error", error);
        if (title != null && !title.isBlank()) {
            model.addAttribute("movie", new MovieDTO(
                    null,
                    title,
                    directors != null ? Arrays.asList(directors.split(",")) : List.of(),
                    description,
                    runningTime != null && !runningTime.isBlank() ? Long.parseLong(runningTime) : null,
                    releaseYear != null && !releaseYear.isBlank() ? LocalDate.parse(releaseYear) : null,
                    null,
                    List.of()
            ));
        }
        return "movie-form";
    }

    @GetMapping("/movies/{id}/edit")
    public String editMovieForm(
            @PathVariable Long id,
            @ModelAttribute("error") String error,
            Model model) {
        model.addAttribute("movie", movieService.getMovieDTO(id));
        if (error != null && !error.isBlank()) model.addAttribute("error", error);
        return "movie-form";
    }

    @PostMapping("/movies/create")
    public String createMovie(
            @RequestParam String title,
            @RequestParam String directors,
            @RequestParam(required = false) String description,
            @RequestParam Long runningTime,
            @RequestParam String releaseYear,
            RedirectAttributes redirectAttributes) {
        try {
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
        } catch (DuplicateEntityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("formData_title", title);
            redirectAttributes.addFlashAttribute("formData_directors", directors);
            redirectAttributes.addFlashAttribute("formData_description", description);
            redirectAttributes.addFlashAttribute("formData_runningTime", String.valueOf(runningTime));
            redirectAttributes.addFlashAttribute("formData_releaseYear", releaseYear);
            return "redirect:/movies/create";
        }
    }

    @PostMapping("/movies/{id}/edit")
    public String updateMovie(
            @PathVariable Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String directors,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long runningTime,
            @RequestParam(required = false) String releaseYear,
            RedirectAttributes redirectAttributes) {
        try {
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
        } catch (DuplicateEntityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/movies/" + id + "/edit";
        }
    }

    @PostMapping("/movies/{id}/delete")
    public String deleteMovie(@PathVariable Long id) {
        movieService.delete(id);
        return "redirect:/";
    }

    // --- REVIEW VIEWS ---

    @GetMapping("/reviews")
    public String reviews(
            @RequestParam(required = false) Long movieId,
            @RequestParam(required = false) Double ratingMin,
            @RequestParam(required = false) Double ratingMax,
            @RequestParam(required = false) String author,
            @PageableDefault(size = 20, sort = "reviewDate", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {
        ReviewFilter filter = new ReviewFilter(movieId, ratingMin, ratingMax, author);
        try {
            model.addAttribute("reviews", reviewService.search(filter, pageable));
        } catch (IllegalArgumentException e) {
            model.addAttribute("reviews", Page.empty());
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("filter", filter);
        model.addAttribute("pageable", pageable);
        return "reviews";
    }

    @GetMapping("/reviews/{id}")
    public String review(@PathVariable Long id, Model model) {
        model.addAttribute("review", reviewService.getReviewDTO(id));
        return "review";
    }

    @GetMapping("/movies/{id}/review")
    public String createReviewForm(
            @PathVariable Long id,
            @ModelAttribute("error") String error,
            Model model) {
        model.addAttribute("movieId", id);
        if (error != null && !error.isBlank()) model.addAttribute("error", error);
        return "review-form";
    }

    @GetMapping("/reviews/{id}/edit")
    public String editReviewForm(
            @PathVariable Long id,
            @ModelAttribute("error") String error,
            Model model) {
        ReviewDTO review = reviewService.getReviewDTO(id);
        model.addAttribute("movieId", review.movieId());
        model.addAttribute("review", review);
        if (error != null && !error.isBlank()) model.addAttribute("error", error);
        return "review-form";
    }

    @PostMapping("/reviews/create")
    public String createReview(
            @RequestParam Long movieId,
            @RequestParam Double reviewRating,
            @RequestParam String reviewAuthor,
            @RequestParam(required = false) String reviewText,
            RedirectAttributes redirectAttributes) {
        try {
            reviewService.create(new CreateReviewDTO(
                    movieId,
                    reviewRating,
                    reviewAuthor,
                    reviewText
            ));
            return "redirect:/movies/" + movieId;
        } catch (DuplicateEntityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/movies/" + movieId + "/review";
        }
    }

    @PostMapping("/reviews/{id}/edit")
    public String updateReview(
            @PathVariable Long id,
            @RequestParam Long movieId,
            @RequestParam(required = false) Double reviewRating,
            @RequestParam(required = false) String reviewAuthor,
            @RequestParam(required = false) String reviewText,
            RedirectAttributes redirectAttributes) {
        try {
            reviewService.update(new UpdateReviewDTO(
                    id,
                    reviewRating,
                    reviewAuthor,
                    reviewText != null ? Optional.of(reviewText) : null
            ));
            return "redirect:/movies/" + movieId;
        } catch (DuplicateEntityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/reviews/" + id + "/edit";
        }
    }

    @PostMapping("/reviews/{id}/delete")
    public String deleteReview(@PathVariable Long id) {
        ReviewDTO review = reviewService.getReviewDTO(id);
        Long movieId = review.movieId();
        reviewService.delete(id);
        return "redirect:/movies/" + movieId;
    }
}