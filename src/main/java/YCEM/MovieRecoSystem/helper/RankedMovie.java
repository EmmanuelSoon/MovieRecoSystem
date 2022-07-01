package YCEM.MovieRecoSystem.helper;

import YCEM.MovieRecoSystem.model.Movie;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class RankedMovie {
    private Movie movie;
    private double value;
    public RankedMovie(Movie movie, double value) {
        this.movie = movie;
        this.value = value;
    }
}
