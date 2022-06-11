package YCEM.MovieRecoSystem.model;

import YCEM.MovieRecoSystem.filter.*;
import org.springframework.beans.factory.annotation.Autowired;
import YCEM.MovieRecoSystem.repo.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchEngine {

    @Autowired
    RatingRepository ratingRepo;
    @Autowired
    RaterRepository raterRepo;
    @Autowired
    MovieRepository movieRepository;

    public SearchEngine() { }

    public double getAverageByID(int id, int minimalRaters)
    {
        List<Rating> ratings = ratingRepo.findAllByMovieId(id);
        if (ratings.size() >= minimalRaters){
            int numRaters = 0;
            double sumRatings = 0.0;
            for (Rating r : ratings){
                numRaters++;
                sumRatings += r.getValue();
            }
            return sumRatings/numRaters;
        }
        else {
            return 0.0;
        }
    }

    public List<Rating> getAverageRatings(int minimalRaters){
        //Return list of movies with at least minimalRaters' rating
        List<Rating> res = new ArrayList<>();
        List<Movie> movies = movieRepository.findAll();
        for (Movie movie : movies)
        {
            double avg = getAverageByID(movie.getId(), minimalRaters);
            if (avg != 0.0)
            {
                res.add(new Rating(movie, avg));
            }
        }
        return res;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> res = new ArrayList<>();
        for (Rating rating : getAverageRatings(minimalRaters))
        {
            if (filterCriteria.satisfies(rating.getMovie()))
            {
                res.add(rating);
            }
        }
        return res;
    }

}