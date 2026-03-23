package org.example.sbmdb.controller;

import jakarta.validation.ConstraintViolation;
import org.example.sbmdb.entity.dto.*;
import org.example.sbmdb.error.DuplicateEntityException;
import org.example.sbmdb.filter.MovieFilter;
import org.example.sbmdb.filter.ReviewFilter;
import org.example.sbmdb.service.MovieService;
import org.example.sbmdb.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ViewController {

    private static final String MOVIES_VIEW = "movies";
    private static final String MOVIE_VIEW = "movie";
    private static final String MOVIE_FORM_VIEW = "movie-form";
    private static final String REVIEWS_VIEW = "reviews";
    private static final String REVIEW_VIEW = "review";
    private static final String REVIEW_FORM_VIEW = "review-form";
    private static final String REDIRECT_HOME = "redirect:/";

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final jakarta.validation.Validator validator;

    public ViewController(MovieService movieService, ReviewService reviewService, jakarta.validation.Validator validator) {
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.validator = validator;
    }

    // --- HELPERS ---

    private List<String> parseDirectors(String directors) {
        if (directors == null) return null;
        return Arrays.stream(directors.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }

    private LocalDate parseDate(String date) {
        return date != null && !date.isBlank() ? LocalDate.parse(date) : null;
    }

    private MovieDTO buildMovieDTOFromParams(String title, List<String> directorList, String description, Long runningTime, String releaseYear) {
        return new MovieDTO(null, title, directorList, description,
                runningTime, parseDate(releaseYear), null, List.of());
    }

    private Sort parseSort(String sort) {
        String[] sortParts = sort.split(",");
        Sort.Direction direction = sortParts.length > 1 && sortParts[1].equals("asc")
                ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(direction, sortParts[0]);
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
            @RequestParam(required = false, defaultValue = "title,asc") String sort,
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
        model.addAttribute("currentSort", sort);
        model.addAttribute("pageable", pageable);
        return MOVIES_VIEW;
    }

    @GetMapping("/movies/{id}")
    public String movie(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "reviewDate,desc") String sort,
            Model model) {
        model.addAttribute("movie", movieService.getMovieDTO(id, parseSort(sort)));
        model.addAttribute("currentSort", sort);
        return MOVIE_VIEW;
    }

    @GetMapping("/movies/create")
    public String createMovieForm() {
        return MOVIE_FORM_VIEW;
    }

    @GetMapping("/movies/{id}/edit")
    public String editMovieForm(
            @PathVariable Long id,
            @ModelAttribute("error") String error,
            Model model) {
        model.addAttribute("movie", movieService.getMovieDTO(id));
        if (error != null && !error.isBlank()) model.addAttribute("error", error);
        return MOVIE_FORM_VIEW;
    }

    @PostMapping("/movies/create")
    public String createMovie(
            @RequestParam String title,
            @RequestParam String directors,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long runningTime,
            @RequestParam(required = false) String releaseYear,
            Model model) {
        List<String> directorList = parseDirectors(directors);

        CreateMovieDTO dto = new CreateMovieDTO(
                title,
                directorList,
                description,
                runningTime,
                parseDate(releaseYear)
        );

        Set<ConstraintViolation<CreateMovieDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .toList());
            model.addAttribute("movie", buildMovieDTOFromParams(title, directorList, description, runningTime, releaseYear));
            return MOVIE_FORM_VIEW;
        }

        try {
            movieService.create(dto);
            return REDIRECT_HOME;
        } catch (DuplicateEntityException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("movie", buildMovieDTOFromParams(title, directorList, description, runningTime, releaseYear));
            return MOVIE_FORM_VIEW;
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
            movieService.update(new UpdateMovieDTO(
                    id,
                    title,
                    parseDirectors(directors),
                    description != null ? Optional.of(description) : null,
                    runningTime,
                    parseDate(releaseYear)
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
        return REDIRECT_HOME;
    }

    // --- REVIEW VIEWS ---

    @GetMapping("/reviews")
    public String reviews(
            @RequestParam(required = false) Long movieId,
            @RequestParam(required = false) Double ratingMin,
            @RequestParam(required = false) Double ratingMax,
            @RequestParam(required = false) String author,
            @RequestParam(required = false, defaultValue = "reviewDate,desc") String sort,
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
        model.addAttribute("currentSort", sort);
        model.addAttribute("pageable", pageable);
        return REVIEWS_VIEW;
    }

    @GetMapping("/reviews/{id}")
    public String review(@PathVariable Long id, Model model) {
        model.addAttribute("review", reviewService.getReviewDTO(id));
        return REVIEW_VIEW;
    }

    @GetMapping("/movies/{id}/review")
    public String createReviewForm(@PathVariable Long id, Model model) {
        model.addAttribute("movieId", id);
        return REVIEW_FORM_VIEW;
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
        return REVIEW_FORM_VIEW;
    }

    @PostMapping("/reviews/create")
    public String createReview(
            @RequestParam Long movieId,
            @RequestParam(required = false) Double reviewRating,
            @RequestParam(required = false) String reviewAuthor,
            @RequestParam(required = false) String reviewText,
            Model model) {
        CreateReviewDTO dto = new CreateReviewDTO(movieId, reviewRating, reviewAuthor, reviewText);

        Set<ConstraintViolation<CreateReviewDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .toList());
            model.addAttribute("movieId", movieId);
            return REVIEW_FORM_VIEW;
        }

        try {
            reviewService.create(dto);
            return "redirect:/movies/" + movieId;
        } catch (DuplicateEntityException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("movieId", movieId);
            return REVIEW_FORM_VIEW;
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