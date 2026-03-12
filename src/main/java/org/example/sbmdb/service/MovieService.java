package org.example.sbmdb.service;


import org.example.sbmdb.entity.DTO.CreateMovieDTO;
import org.example.sbmdb.entity.DTO.MovieDTO;
import org.example.sbmdb.entity.DTO.UpdateMovieDTO;
import org.example.sbmdb.entity.Movie;
import org.example.sbmdb.entity.mapper.MovieMapper;
import org.example.sbmdb.error.*;
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

        if(!movieRepo.isUnique(movie)){
            throw new DuplicateEntityException("Movie");
        }

        movieRepo.save(MovieMapper.createMovie(dto));
    }

    public Movie getMovie(Long id){
        return movieRepo.findById(id).
                orElseThrow(()->new EntityNotFoundException("Movie", id));
    }

    public MovieDTO getMovieDTO(Long id){
        return MovieMapper.createMovieDTO(getMovie(id));
    }

    public void update(UpdateMovieDTO dto){
        Movie movie = getMovie(dto.id());

        MovieMapper.updateMovie(movie, dto);
        movieRepo.save(movie);
    }

    public void delete(Long id){
        Movie movie = getMovie(id);

        movieRepo.delete(movie);
    }
}
