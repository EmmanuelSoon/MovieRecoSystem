package YCEM.MovieRecoSystem.helper;

import YCEM.MovieRecoSystem.model.Movie;
import YCEM.MovieRecoSystem.model.Rating;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;

@Data
@NoArgsConstructor
public class UserRating {

    public UserRating(Movie movie, double rating ) {
        this.movie = movie;
        this.rating = rating;
    }

    public UserRating(Movie movie) {
        this.movie = movie;
        this.rating = 0.0;
    }

    private Movie movie;
    private double rating;
}
