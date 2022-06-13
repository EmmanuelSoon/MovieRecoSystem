package YCEM.MovieRecoSystem.service;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
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

@Service
public class CSVservice {

    @Autowired
    private MovieRepository movieRepo;
  
    @Autowired
    private RaterRepository raterRepo;

    @Autowired
    private RatingRepository ratingRepo;
  


    public void csvInit(){
        String moviePath = "./src/main/resources/data/ratedmovies_short.csv";
        String ratingPath = "./src/main/resources/data/ratings_short.csv";

        List<Movie> movies = csvToMovie(moviePath);
        movieRepo.saveAll(movies);

        List<Rater> raters = csvToRater(ratingPath);
        raterRepo.saveAll(raters);

        List<Rating> ratings = csvToRatings(ratingPath);
        ratingRepo.saveAll(ratings);
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

    public List<Movie> csvToMovie(String path) {
      try
      (
        Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
      ) {
        List<Movie> movies = new ArrayList<Movie>();
        String[] nextRecord;
        while ((nextRecord = csvReader.readNext()) != null) 
        {
          Movie curr_movie = new Movie(
            Integer.parseInt(nextRecord[0]),
            nextRecord[1],
            Integer.parseInt(nextRecord[2]),
            nextRecord[3],
            nextRecord[4],
            nextRecord[5],
            Integer.parseInt(nextRecord[6]),
            nextRecord[7],
            new HashSet<Rating>()
          );
          movies.add(curr_movie);
        } 
      return movies;

  } catch (IOException e) {
    throw new RuntimeException("fail to parse Movie file: " + e.getMessage());
    }
  }


  public List<Rater> csvToRater(String path) {
    try
    (
      Reader reader = Files.newBufferedReader(Paths.get(path));
      CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
    ) {
      List<Rater> raters = new ArrayList<Rater>();
      String[] nextRecord;
      while ((nextRecord = csvReader.readNext()) != null) 
      {
        Rater curr_rater = new Rater(
          Integer.parseInt(nextRecord[0]),
          new ArrayList<Rating>()
        );
        if (!raters.contains(curr_rater)){
          raters.add(curr_rater);
        }

      } 
    return raters;

  } catch (IOException e) {
    throw new RuntimeException("fail to get Raters " + e.getMessage());
  }
}
  
}
