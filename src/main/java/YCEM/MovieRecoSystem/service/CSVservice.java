package YCEM.MovieRecoSystem.service;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.opencsv.CSVReaderBuilder;

import org.apache.commons.csv.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
        String moviePath = "./src/main/resources/data/ratedmoviesfull.csv";
        String ratingPath = "./src/main/resources/data/ratings.csv";

        System.out.println("test");
        List<Movie> movies = csvToMovie(moviePath);
        movieRepo.saveAll(movies);

        System.out.println("test1");
        List<Rater> raters = csvToRater(ratingPath);
        raterRepo.saveAll(raters);

        List<Rating> ratings = csvToRatings(ratingPath);
        ratingRepo.saveAll(ratings);

        // addingPosterUrl();
        // System.out.println("Seeding Completed");
    }





    public List<Movie> csvToMovie(String path)  {
      String[] HEADERS = {"id","title","year","country","genre","director","minutes","poster"};
      try(
        Reader reader = new FileReader(path);
      ){
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(HEADERS).withFirstRecordAsHeader().parse(reader);
        List<Movie> movies = new ArrayList<Movie>();
        //int id, String title, int yearPublished, String genres, String director, String country, int minutes, String poster, Set<Rating> ratings
        for (CSVRecord record : records){
          Movie curr_movie = new Movie(
            Integer.parseInt(record.get("id")),
            record.get("title"),
            Integer.parseInt(record.get("year")),
            record.get("genre"),
            record.get("director"),
            record.get("country"),
            Integer.parseInt(record.get("minutes")),
            "null"
          );
          movies.add(curr_movie);
        } 
      return movies;
      } catch (IOException e) {
          throw new RuntimeException("fail to get Raters " + e.getMessage());
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
      rating.setMovieId(Integer.parseInt(nextRecord[1]));
      ratings.add(rating);

    }
    return ratings;
    } catch (IOException e) {
    throw new RuntimeException("fail to get Ratings: " + e.getMessage());
      }
  }


  public void addingPosterUrl(){
    List<Movie> movies = movieRepo.findAll();
    for(Movie movie : movies){
      WebClient webClient = WebClient.create("https://api.themoviedb.org/3");

      SearchResult response = webClient.get()
          .uri(uriBuilder -> uriBuilder
              .path("/search/movie")
              .queryParam("api_key", "c39d7f9fe2d8ee821511b5f3d66d45c7")
              .queryParam("query", movie.getTitle())
              .build())
              .retrieve()
              .bodyToMono(SearchResult.class).block();

      for (Result result : response.results){
        String title = result.title;
        if (title.equals(movie.getTitle())){
          movie.setPoster(result.poster_path);
        }
      }
      movieRepo.save(movie);
    }
  }
}
