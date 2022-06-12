package YCEM.MovieRecoSystem.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@ToString
public class Movie {
    @Id
    private int id;
    private String title;
    private int yearPublished;
    private String genres;
    private String director;
    private String country;
    private int minutes;
    private String poster;
    @OneToMany(mappedBy = "movie")
    private Set<Rating> ratings;

    //contructor


    public Movie(int id, String title, int yearPublished, String genres, String director, String country, int minutes, String poster, Set<Rating> ratings) {
        this.id = id;
        this.title = title;
        this.yearPublished = yearPublished;
        this.genres = genres;
        this.director = director;
        this.country = country;
        this.minutes = minutes;
        this.poster = poster;
        this.ratings = ratings;
    }

    public Movie(String title, int yearPublished, String genres, String director, String country, int minutes, String poster, Set<Rating> ratings) {
        this.title = title;
        this.yearPublished = yearPublished;
        this.genres = genres;
        this.director = director;
        this.country = country;
        this.minutes = minutes;
        this.poster = poster;
        this.ratings = ratings;
    }

    public Movie(){
        this.ratings = new HashSet<Rating>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return getId() == movie.getId() && getYearPublished() == movie.getYearPublished() && getMinutes() == movie.getMinutes() && Objects.equals(getTitle(), movie.getTitle()) && Objects.equals(getGenres(), movie.getGenres()) && Objects.equals(getDirector(), movie.getDirector()) && Objects.equals(getCountry(), movie.getCountry()) && Objects.equals(getPoster(), movie.getPoster()) && Objects.equals(getRatings(), movie.getRatings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getYearPublished(), getGenres(), getDirector(), getCountry(), getMinutes(), getPoster(), getRatings());
    }
}
