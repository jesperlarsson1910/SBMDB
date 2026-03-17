package org.example.sbmdb.controller;

import jakarta.validation.Valid;
import org.example.sbmdb.entity.dto.*;
import org.example.sbmdb.filter.MovieFilter;
import org.example.sbmdb.service.MovieService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateMovieDTO dto) {
        movieService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovie(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieDTO(id));
    }

    @GetMapping
    public ResponseEntity<Page<MovieSummaryDTO>> getMovies(
            @ParameterObject MovieFilter filter,
            @ParameterObject @PageableDefault(size = 20, sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(movieService.search(filter, pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody UpdateMovieDTO dto) {
        movieService.update(new UpdateMovieDTO(
                id,
                dto.title(),
                dto.directors(),
                dto.description(),
                dto.runningTime(),
                dto.releaseYear()
        ));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}