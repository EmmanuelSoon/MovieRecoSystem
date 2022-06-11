package YCEM.MovieRecoSystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;
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
    @OneToMany(mappedBy="raterId")
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
}
