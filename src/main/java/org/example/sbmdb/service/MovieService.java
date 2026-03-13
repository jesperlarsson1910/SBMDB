package org.example.sbmdb.service;


import org.example.sbmdb.entity.DTO.CreateMovieDTO;
import org.example.sbmdb.entity.DTO.MovieDTO;
import org.example.sbmdb.entity.DTO.MovieSummaryDTO;
import org.example.sbmdb.entity.DTO.UpdateMovieDTO;
import org.example.sbmdb.entity.Movie;
import org.example.sbmdb.entity.mapper.MovieMapper;
import org.example.sbmdb.error.*;
import org.example.sbmdb.repository.MovieRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepo movieRepo;

    public MovieService(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    public void create(CreateMovieDTO dto){
        Movie movie = MovieMapper.createMovie(dto);

        if(!movieRepo.isUnique(movie)){
            throw new DuplicateEntityException("Movie");
        }

        movieRepo.save(movie);
    }

    public Movie getMovie(Long id){
        return movieRepo.findById(id).
                orElseThrow(()->new EntityNotFoundException("Movie", id));
    }

    public MovieDTO getMovieDTO(Long id){
        return MovieMapper.createMovieDTO(getMovie(id));
    }

    @Transactional
    public void update(UpdateMovieDTO dto){
        Movie movie = getMovie(dto.id());

        MovieMapper.updateMovie(movie, dto);
        movieRepo.save(movie);
    }

    @Transactional
    public void delete(Long id){
        movieRepo.delete(getMovie(id));
    }

    @Transactional
    public void updateMovieRating(Long id) {
        Movie movie = getMovie(id);

        movie.updateRating();
        movieRepo.save(movie);
    }

    public Page<MovieSummaryDTO> getMovies(Pageable pageable) {
        return movieRepo.findAll(pageable)
                .map(MovieMapper::createMovieSummaryDTO);
    }

    public Page<MovieSummaryDTO> findByTitle(String title, Pageable pageable) {
        return movieRepo.findByTitleContaining(title, pageable)
                .map(MovieMapper::createMovieSummaryDTO);
    }

    public Page<MovieSummaryDTO> findByDirector(String director, Pageable pageable) {
        return movieRepo.findByDirectorsContaining(director, pageable)
                .map(MovieMapper::createMovieSummaryDTO);
    }

    public Page<MovieSummaryDTO> findByDescription(String description, Pageable pageable) {
        return movieRepo.findByDescriptionContaining(description, pageable)
                .map(MovieMapper::createMovieSummaryDTO);
    }

    public Page<MovieSummaryDTO> findByRunningTimeBetween(Long low, Long high, Pageable pageable) {
        return movieRepo.findByRunningTimeBetween(low, high, pageable)
                .map(MovieMapper::createMovieSummaryDTO);
    }

    public Page<MovieSummaryDTO> findByReleaseYearBetween(LocalDate low, LocalDate high, Pageable pageable) {
        return movieRepo.findByReleaseYearBetween(low, high, pageable)
                .map(MovieMapper::createMovieSummaryDTO);
    }

    public Page<MovieSummaryDTO> findByRatingBetween(Double low, Double high, Pageable pageable) {
        return movieRepo.findByRatingBetween(low, high, pageable)
                .map(MovieMapper::createMovieSummaryDTO);
    }
}
