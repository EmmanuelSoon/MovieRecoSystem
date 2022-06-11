package YCEM.MovieRecoSystem.model;

import java.util.Objects;

public class RankedMovie {
    private Movie movie;
    private double value;

    public RankedMovie() {
    }

    public RankedMovie(Movie movie, double value) {
        this.movie = movie;
        this.value = value;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RankedMovie)) return false;
        RankedMovie that = (RankedMovie) o;
        return Double.compare(that.getValue(), getValue()) == 0 && Objects.equals(getMovie(), that.getMovie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovie(), getValue());
    }
}
