package org.example.sbmdb.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.sbmdb.entity.DTO.CreateMovieDTO;
import org.example.sbmdb.entity.DTO.UpdateMovieDTO;
import org.example.sbmdb.entity.Movie;
import org.example.sbmdb.entity.mapper.MovieMapper;
import org.example.sbmdb.repository.MovieRepo;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepo movieRepo;

    public MovieService(MovieRepo movieRepo) {
        this.movieRepo = movieRepo;
    }

    public void create(CreateMovieDTO dto){
        Movie movie = MovieMapper.createMovie(dto);
        movieRepo.save(movie);
    }

    public void update(UpdateMovieDTO dto){
        Movie movie = movieRepo.findById(dto.id()).
                orElseThrow(()->new EntityNotFoundException("Movie not found"));
    }
}
