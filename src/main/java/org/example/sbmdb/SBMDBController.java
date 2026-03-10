package org.example.sbmdb;

import org.example.sbmdb.repository.MovieRepo;
import org.example.sbmdb.repository.ReviewRepo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SBMDBController {

    private MovieRepo movieRepo;
    private ReviewRepo reviewRepo;

    public SBMDBController(MovieRepo movieRepo, ReviewRepo reviewRepo) {
        this.movieRepo = movieRepo;
        this.reviewRepo = reviewRepo;
    }


}
