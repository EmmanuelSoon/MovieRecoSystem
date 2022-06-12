package YCEM.MovieRecoSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import YCEM.MovieRecoSystem.model.*;
import YCEM.MovieRecoSystem.repo.*;


public class CSVservice {

    @Autowired
    private MovieRepository movieRepo;
  
    @Autowired
    private RaterRepository raterRepo;

    @Autowired
    private RatingRepository ratingRepo;
  


    public void csvInit(){
        CSVHelper helper = new CSVHelper();

        String moviePath = "./src/main/resources/data/ratedmoviesfull.csv";
        String ratingPath = "/src/main/resources/data/ratings.csv";

        List<Movie> movies = helper.csvToMovie(moviePath);
        movieRepo.saveAllAndFlush(movies);

        List<Rater> raters = helper.csvToRater(ratingPath);
        raterRepo.saveAllAndFlush(raters);

        List<Rating> ratings = helper.csvToRatings(ratingPath);
        ratingRepo.saveAllAndFlush(ratings);
    }
}
