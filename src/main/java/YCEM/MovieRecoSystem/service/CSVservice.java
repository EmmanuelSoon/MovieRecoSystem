package YCEM.MovieRecoSystem.service;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.opencsv.CSVReaderBuilder;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import YCEM.MovieRecoSystem.model.*;
import YCEM.MovieRecoSystem.repo.*;
import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class CSVservice {

    @Autowired
    private MovieRepository movieRepo;
  
    @Autowired
    private RaterRepository raterRepo;

    @Autowired
    private RatingRepository ratingRepo;
  


    public void csvInit(){
        CSVHelper helper = new CSVHelper();

        String moviePath = "./src/main/resources/data/ratedmovies_short.csv";
        String ratingPath = "./src/main/resources/data/ratings_short.csv";

        List<Movie> movies = helper.csvToMovie(moviePath);
        System.out.println("===============================");
        for(Movie movie: movies){
            System.out.println(movie);
        }
        movieRepo.saveAll(movies);

        List<Rater> raters = helper.csvToRater(ratingPath);
        System.out.println("===============================");
        for(Rater rater: raters){
            System.out.println(rater);
        }
        raterRepo.saveAll(raters);

        List<Rating> ratings = csvToRatings(ratingPath);
        System.out.println("===============================");
        for(Rating rating: ratings){
            System.out.println(rating.getId());
        }

        // ratingRepo.saveAll(ratings);
    }


    public List<Rating> csvToRatings(String path) {
        try
        (
          Reader reader = Files.newBufferedReader(Paths.get(path));
          CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
        ) {
          List<Rating> ratings = new ArrayList<Rating>();
          String[] nextRecord;
          while ((nextRecord = csvReader.readNext()) != null) {
            Rating rating = new Rating();
            rating.setRatedValue(Double.parseDouble(nextRecord[2]));;
            Movie movie = movieRepo.getReferenceById(Integer.parseInt(nextRecord[1]));
            rating.setMovie(movie);
            Rater rater = raterRepo.getReferenceById(Integer.parseInt(nextRecord[0]));
            rating.setRater(rater);
  
            ratings.add(rating);
  
        }
      return ratings;
    } catch (IOException e) {
      throw new RuntimeException("fail to get Ratings: " + e.getMessage());
        }
      }
  
}
