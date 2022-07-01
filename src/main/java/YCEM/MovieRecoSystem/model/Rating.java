package YCEM.MovieRecoSystem.model;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ratingid")
    private int id;
    @Column(name = "movieid")
    private int movieId;
    private double ratedValue;


    public Rating(int movieId, double ratedValue) {
        this.movieId = movieId;
        this.ratedValue = ratedValue;
    }

    public int compareTo(Rating other){
        if (this.ratedValue - other.getRatedValue() > 0)
            return 1;
        else if (this.ratedValue - other.getRatedValue() < 0)
            return -1;
        else
            return 0;
    }
}
