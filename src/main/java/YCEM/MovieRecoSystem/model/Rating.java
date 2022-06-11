package YCEM.MovieRecoSystem.model;

import javax.persistence.Entity;
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

    public Rating(Movie movie, double value) {
        this.movie = movie;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;
        Rating rating = (Rating) o;
        return getId() == rating.getId() && Double.compare(rating.getValue(), getValue()) == 0 && Objects.equals(getMovie(), rating.getMovie()) && Objects.equals(getRater(), rating.getRater());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMovie(), getRater(), getValue());
    }
}
