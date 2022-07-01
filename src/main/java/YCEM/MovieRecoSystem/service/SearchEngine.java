package YCEM.MovieRecoSystem.service;

import YCEM.MovieRecoSystem.filter.*;
import YCEM.MovieRecoSystem.helper.RankedMovie;
import YCEM.MovieRecoSystem.helper.SimilarRater;
import YCEM.MovieRecoSystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import YCEM.MovieRecoSystem.repo.*;
import org.springframework.stereotype.Service;


import java.util.*;
@Service
public class SearchEngine {

    @Autowired
    RatingRepository ratingRepo;
    @Autowired
    RaterRepository raterRepo;
    @Autowired
    MovieRepository movieRepository;

    public SearchEngine() { }

    //get average rating for movie with minimalRaters provided
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
                res.add(new Rating(movie.getId(), avg));
            }
        }
        return res;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria){
        ArrayList<Rating> res = new ArrayList<>();
        for (Rating rating : getAverageRatings(minimalRaters))
        {
            Optional<Movie> movie = movieRepository.findById(rating.getMovieId());
            if (movie.isPresent() && filterCriteria.satisfies(movie.get()))
            {
                res.add(rating);
            }
        }
        return res;
    }

    // return the calculated dotProduct based on the common movies that both rater rated to indicate similarity
    private double dotProduct(Rater me, Rater r) {
        double dotProduct = 0;
        for (Rating myRating : me.getRatings())
        {
            double otherRatedVal = r.getRating(myRating.getMovieId());
            if (otherRatedVal != -1.0) {
                dotProduct += (myRating.getRatedValue()-5)*(otherRatedVal-5);
            }
        }
        return dotProduct;
    }

    private ArrayList<SimilarRater> getSimilarities(Rater me){
        ArrayList<SimilarRater> res = new ArrayList<>();
        if (me != null)
        {
            List<Rater> raters = raterRepo.findAll();
            for (Rater other : raters)
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
        return res;
    }

    public ArrayList<RankedMovie> getSimilarRatings(Rater me, int numSimilarRaters, int minimalRaters) {
        return getSimilarRatingsByFilter(me, numSimilarRaters, minimalRaters, new TrueFilter());
    }

    /*
    * raterId = current user/rater Id
    * size = Nos of recommendated movies required
    * numSimilarRaters = Nos of similarRater needed to build Recommendation
    * minimalRaters = min Nos of raters required to consider a weighted movie
    * Filter f
    * */
    public ArrayList<Movie> getRecommendedMovie(Rater me, int size, int numSimilarRaters, int minimalRaters, Filter filter) {
        ArrayList<RankedMovie> moviesList = getSimilarRatingsByFilter(me, numSimilarRaters, minimalRaters, filter);
        ArrayList<Movie> res = new ArrayList<>();
        for (RankedMovie rankedMovie : moviesList) {
            res.add(rankedMovie.getMovie());
            if (res.size() >= size) {
                break;
            }
        }
        return res;
    }

    public ArrayList<RankedMovie> getSimilarRatingsByFilter(Rater me, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {

        // getTopSimilarRaters with size of numSimilarRaters
        ArrayList<SimilarRater> topSimilarRaters = getTopSimilarRaters(getSimilarities(me), numSimilarRaters);

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

    //
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
            if ( rating != -1.0)
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