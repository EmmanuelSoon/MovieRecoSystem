package YCEM.MovieRecoSystem.model;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name = "rater")
public class Rater {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "raterid")
    private int id;
    
    @OneToMany(fetch = FetchType.EAGER)
    private List<Rating> ratings = new ArrayList<Rating>();;

    public Rater(int id, List<Rating> ratings) {
        this.id = id;
        this.ratings = ratings;
    }
    public void addRating(Rating r){
        ratings.add(r);
    }

    public double getRating(int movieId){
        for (Rating r : getRatings()){
            if (r.getMovieId() == movieId)
                return r.getRatedValue();
        }
        return -1.0;
    }
}
