package YCEM.MovieRecoSystem.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimilarRater {

    //Rater ID
    private int id;
    private double dotProductValue;

    public SimilarRater(int id, double dotProductValue) {
        this.id = id;
        this.dotProductValue = dotProductValue;
    }
}
