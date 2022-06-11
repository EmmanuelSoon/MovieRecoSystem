package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
public class Rating {
    @Id
    private int id;
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Rater rater;
    private double value;

    public Rating(Movie movie, Rater rater, double value) {
        this.movie = movie;
        this.rater = rater;
        this.value = value;
    }
    public int compareTo(Rating other){
        if (this.value - other.getValue() > 0)
            return 1;
        else if (this.value - other.getValue() < 0)
            return -1;
        else
            return 0;
    }
}
