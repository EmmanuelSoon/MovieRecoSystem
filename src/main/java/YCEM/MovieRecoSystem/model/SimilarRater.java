package YCEM.MovieRecoSystem.model;

public class SimilarRater {

    //Rater ID
    private int id;
    private double dotProductValue;

    public SimilarRater(int id, double dotProductValue) {
        this.id = id;
        this.dotProductValue = dotProductValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDotProductValue() {
        return dotProductValue;
    }

    public void setDotProductValue(double dotProductValue) {
        this.dotProductValue = dotProductValue;
    }

}
