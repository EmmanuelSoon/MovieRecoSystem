package YCEM.MovieRecoSystem.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Data
@NoArgsConstructor
@ToString
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Movie movie;
    @ManyToOne
    private Rater rater;
    private double ratedValue;

    public Rating(Movie movie, Rater rater, double ratedValue) {
        this.movie = movie;
        this.rater = rater;
        this.ratedValue = ratedValue;
    }

    public Rating(Movie movie, double ratedValue) {
        this.movie = movie;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;
        Rating rating = (Rating) o;
        return getId() == rating.getId() && Double.compare(rating.getRatedValue(), getRatedValue()) == 0 && Objects.equals(getMovie(), rating.getMovie()) && Objects.equals(getRater(), rating.getRater());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMovie(), getRater(), getRatedValue());
    }
}
