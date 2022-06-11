package YCEM.MovieRecoSystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@ToString
@Entity
public class Rater {

    @Id
    private int id;
    @OneToMany(mappedBy="id")
    private List<Rating> ratings;

    public Rater(int id, List<Rating> ratings) {
        this.id = id;
        this.ratings = ratings;
    }

    public Rater(){
        this.ratings = new ArrayList<Rating>();
    }

    public void addRating(Rating r){
        ratings.add(r);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rater)) return false;
        Rater rater = (Rater) o;
        return getId() == rater.getId() && Objects.equals(getRatings(), rater.getRatings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRatings());
    }

    public double getRating(int id){
        for (Rating r : getRatings()){
            if (r.getMovie().getId() == id)
                return r.getValue();
        }
        return -1.0;
    }
}
