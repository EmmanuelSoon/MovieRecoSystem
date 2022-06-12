package YCEM.MovieRecoSystem.model;

import YCEM.MovieRecoSystem.filter.*;
import org.springframework.beans.factory.annotation.Autowired;
import YCEM.MovieRecoSystem.repo.*;


import java.util.*;

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
                sumRatings += r.getRatedValue();
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

    private double dotProduct(Rater me, Rater r){
        double dotProduct = 0;
        for (Rating myRating : me.getRatings())
        {
            double otherRatingVal = r.getRating(myRating.getMovie().getId());
            if (otherRatingVal != -1.0) {
                dotProduct += (myRating.getRatedValue()-5)*(otherRatingVal-5);
            }
        }
        return dotProduct;
    }

    private ArrayList<SimilarRater> getSimilarities(int raterId){
        ArrayList<SimilarRater> res = new ArrayList<>();
        Rater me = raterRepo.findById(raterId).get();
        if (me != null)
        {
            List<Rater> raters = raterRepo.findAll();
            for (Rater other : raters)
            {
                if (other.getId() != raterId)
                {
                    //if current rater is not myself
                    double dotProductValue = dotProduct(me, other);
                    if (dotProductValue > 0)
                    {
                        //only add similar positive dotProductValue
                        res.add(new SimilarRater(other.getId(), dotProductValue));
                    }
                }
            }
        }
        return res;
    }

    public ArrayList<RankedMovie> getSimilarRatings(int raterID, int numSimilarRaters, int minimalRaters){
        return getSimilarRatingsByFilter(raterID, numSimilarRaters, minimalRaters, new TrueFilter());
    }
    public ArrayList<RankedMovie> getSimilarRatingsByFilter(int raterID, int numSimilarRaters, int minimalRaters, Filter filterCriteria){

        ArrayList<SimilarRater> topSimilarRaters = getTopSimilarRaters(getSimilarities(raterID), numSimilarRaters);

        List<Movie> movies =applyFilter(movieRepository.findAll(), filterCriteria);

        //MovieList with weighted value to be returned
        ArrayList<RankedMovie> moviesWithAvgWeight = new ArrayList<>();

        for (Movie movie : movies)
        {
            double weightedAvg = getWeightAvgRatingsBySimilarRaters(movie.getId(), minimalRaters, topSimilarRaters);
            if (weightedAvg != -1.0)
            {
                moviesWithAvgWeight.add(new RankedMovie(movie, weightedAvg));
            }
        }
        //Sort the movies in descending order based on its weighted ratings by topSimilarRaters
        Collections.sort(moviesWithAvgWeight, (a, b) -> Double.compare(b.getValue(), a.getValue()));
        return moviesWithAvgWeight;
    }
    private List<Movie> applyFilter(List<Movie> movieList, Filter filterCriteria) {
        List<Movie> res = new ArrayList<>();
        for (Movie movie : movieList) {
            if (filterCriteria.satisfies(movie)) {
                res.add(movie);
            }
        }
        return res;
    }
    private ArrayList<SimilarRater> getTopSimilarRaters(ArrayList<SimilarRater> similarRaters, int numSimilarRaters)
    {
        Collections.sort(similarRaters, (a, b) -> Double.compare(b.getDotProductValue(), a.getDotProductValue()));
        ArrayList<SimilarRater> topSimilarRaters = new ArrayList<SimilarRater>();
        int size = Math.min(numSimilarRaters, similarRaters.size());
        for (int i = 0; i < size; i++)
        {
            topSimilarRaters.add(similarRaters.get(i));
        }
        return topSimilarRaters;
    }

    private double getWeightAvgRatingsBySimilarRaters(int movieId, int minimalRaters, ArrayList<SimilarRater> topSimilarRaters){
        int numRaters = 0;
        double sumRatings = 0.0;
        for (SimilarRater raterWithWeight : topSimilarRaters)
        {
            Rater rater = raterRepo.findById(raterWithWeight.getId()).get();
            double rating = rater.getRating(movieId);
            if ( rating != -1)
            {
                numRaters++;
                // add product of rater weight and rating into sum
                sumRatings += rating * raterWithWeight.getDotProductValue();
            }
        }
        if (numRaters != 0 && numRaters >= minimalRaters)
        {
            return sumRatings/numRaters;
        }
        else
        {
            return -1.0;
        }
    }

}