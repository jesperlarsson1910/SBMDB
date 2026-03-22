package org.example.sbmdb.utility;

import jakarta.transaction.Transactional;
import org.example.sbmdb.entity.Movie;
import org.example.sbmdb.entity.Review;
import org.example.sbmdb.repository.MovieRepo;
import org.example.sbmdb.repository.ReviewRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final MovieRepo movieRepo;
    private final ReviewRepo reviewRepo;

    public DataLoader(MovieRepo movieRepo, ReviewRepo reviewRepo) {
        this.movieRepo = movieRepo;
        this.reviewRepo = reviewRepo;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (movieRepo.count() == 0) {

            Movie m1 = movieRepo.save(new Movie("Inception", List.of("Christopher Nolan"), "A mind-bending thriller", 148L, LocalDate.of(2010, 7, 16)));
            Movie m2 = movieRepo.save(new Movie("The Godfather", List.of("Francis Ford Coppola"), "A crime dynasty saga", 175L, LocalDate.of(1972, 3, 24)));
            Movie m3 = movieRepo.save(new Movie("Blade Runner 2049", List.of("Denis Villeneuve"), "A neo-noir sci-fi", 164L, LocalDate.of(2017, 10, 6)));
            Movie m4 = movieRepo.save(new Movie("2001: A Space Odyssey", List.of("Stanley Kubrick"), "A journey to Jupiter", 149L, LocalDate.of(1968, 4, 3)));
            Movie m5 = movieRepo.save(new Movie("Parasite", List.of("Bong Joon-ho"), "A tale of two families", 132L, LocalDate.of(2019, 5, 30)));
            Movie m6 = movieRepo.save(new Movie("The Dark Knight", List.of("Christopher Nolan"), "Batman faces the Joker", 152L, LocalDate.of(2008, 7, 18)));
            Movie m7 = movieRepo.save(new Movie("Pulp Fiction", List.of("Quentin Tarantino"), "Interconnected crime stories", 154L, LocalDate.of(1994, 10, 14)));
            Movie m8 = movieRepo.save(new Movie("Spirited Away", List.of("Hayao Miyazaki"), "A girl lost in a spirit world", 125L, LocalDate.of(2001, 7, 20)));
            Movie m9 = movieRepo.save(new Movie("The Shawshank Redemption", List.of("Frank Darabont"), "Hope in a prison", 142L, LocalDate.of(1994, 9, 23)));
            Movie m10 = movieRepo.save(new Movie("Interstellar", List.of("Christopher Nolan"), "A journey through space and time", 169L, LocalDate.of(2014, 11, 7)));
            Movie m11 = movieRepo.save(new Movie("Goodfellas", List.of("Martin Scorsese"), "Rise and fall of a mobster", 146L, LocalDate.of(1990, 9, 19)));
            Movie m12 = movieRepo.save(new Movie("Akira", List.of("Katsuhiro Otomo"), "A dystopian anime epic", 124L, LocalDate.of(1988, 7, 16)));
            Movie m13 = movieRepo.save(new Movie("Alien", List.of("Ridley Scott"), "Terror in deep space", 117L, LocalDate.of(1979, 5, 25)));
            Movie m14 = movieRepo.save(new Movie("Mad Max: Fury Road", List.of("George Miller"), "A post-apocalyptic chase", 120L, LocalDate.of(2015, 5, 15)));
            Movie m15 = movieRepo.save(new Movie("Whiplash", List.of("Damien Chazelle"), "The pursuit of greatness", 107L, LocalDate.of(2014, 10, 10)));
            Movie m16 = movieRepo.save(new Movie("The Seventh Seal", List.of("Ingmar Bergman"), "A knight plays chess with Death", 96L, LocalDate.of(1957, 2, 16)));
            Movie m17 = movieRepo.save(new Movie("City of God", List.of("Fernando Meirelles"), "Crime in the favelas of Rio", 130L, LocalDate.of(2002, 8, 30)));
            Movie m18 = movieRepo.save(new Movie("Everything Everywhere All at Once", List.of("Daniel Kwan", "Daniel Scheinert"), "A multiverse laundry adventure", 139L, LocalDate.of(2022, 3, 25)));
            Movie m19 = movieRepo.save(new Movie("No Country for Old Men", List.of("Joel Coen", "Ethan Coen"), "A hunter stumbles upon a crime", 122L, LocalDate.of(2007, 11, 9)));
            Movie m20 = movieRepo.save(new Movie("Mulholland Drive", List.of("David Lynch"), "A surreal Hollywood mystery", 147L, LocalDate.of(2001, 5, 16)));
            Movie m21 = movieRepo.save(new Movie("The Grand Budapest Hotel", List.of("Wes Anderson"), "A concierge and his lobby boy", 99L, LocalDate.of(2014, 3, 28)));
            Movie m22 = movieRepo.save(new Movie("Oldboy", List.of("Park Chan-wook"), "A man imprisoned for 15 years seeks revenge", 120L, LocalDate.of(2003, 11, 21)));

            reviewRepo.save(new Review(m1, 5.0, "cinephile", "A masterpiece of modern cinema."));
            reviewRepo.save(new Review(m1, 4.5, "filmfan", "Mind blowing from start to finish."));
            reviewRepo.save(new Review(m2, 5.0, "classicmovies", "The greatest film ever made."));
            reviewRepo.save(new Review(m2, 4.5, "marlon_fan", "Brando is untouchable."));
            reviewRepo.save(new Review(m3, 4.0, "scifigeek", "Stunning visuals, slow but rewarding."));
            reviewRepo.save(new Review(m3, 5.0, "villeneuve_fan", "Denis Villeneuve at his best."));
            reviewRepo.save(new Review(m4, 3.5, "kubrick_fan", "Challenging but unforgettable."));
            reviewRepo.save(new Review(m5, 5.0, "bongjoonho", "Perfectly crafted social commentary."));
            reviewRepo.save(new Review(m6, 5.0, "batman_fan", "The greatest superhero film ever."));
            reviewRepo.save(new Review(m7, 4.5, "tarantino_fan", "Non-linear storytelling at its finest."));
            reviewRepo.save(new Review(m8, 5.0, "anime_lover", "Miyazaki's magnum opus."));
            reviewRepo.save(new Review(m9, 5.0, "hopeful", "The most uplifting film ever made."));
            reviewRepo.save(new Review(m10, 4.0, "space_fan", "Emotional and visually stunning."));
            reviewRepo.save(new Review(m11, 4.5, "mob_movies", "Scorsese at his absolute peak."));
            reviewRepo.save(new Review(m12, 4.0, "anime_fan", "Still looks incredible decades later."));
            reviewRepo.save(new Review(m13, 4.5, "horror_fan", "The original and still the best."));
            reviewRepo.save(new Review(m14, 5.0, "action_fan", "Pure adrenaline for two hours."));
            reviewRepo.save(new Review(m15, 5.0, "musician", "Intense and absolutely gripping."));
            reviewRepo.save(new Review(m16, 4.0, "arthouse", "A profound meditation on mortality."));
            reviewRepo.save(new Review(m17, 5.0, "world_cinema", "Raw, visceral and unforgettable."));
            reviewRepo.save(new Review(m18, 4.5, "multiverse_fan", "Weird, wonderful and deeply moving."));
            reviewRepo.save(new Review(m19, 4.5, "coen_fan", "Javier Bardem is terrifying."));
            reviewRepo.save(new Review(m20, 3.5, "lynch_fan", "Deliberately confusing but hypnotic."));
            reviewRepo.save(new Review(m21, 4.0, "wes_fan", "Charming and visually delightful."));
            reviewRepo.save(new Review(m22, 5.0, "korean_cinema", "A shocking and brilliant thriller."));
        }

        movieRepo.findAll().forEach(movie -> {
            movie.updateRating();
            movieRepo.save(movie);
        });
    }
}