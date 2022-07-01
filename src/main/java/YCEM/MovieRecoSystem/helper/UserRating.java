package YCEM.MovieRecoSystem.helper;

import YCEM.MovieRecoSystem.model.Movie;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRating {

    public UserRating(Movie movie, double rating ) {
        this.movie = movie;
        this.rating = rating;
        this.movieid = movie.getId();
    }

    public UserRating(Movie movie) {
        this.movie = movie;
        this.rating = 0.0;
        this.movieid = movie.getId();
    }

    private Movie movie;
    private double rating;
    private int movieid;
}
