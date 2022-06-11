package model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@ToString
public class Movie {
    @Id
    private int id;
    private String title;
    private int year;
    private String genres;
    private String director;
    private String country;
    private int minutes;
    private String poster;
    @OneToMany(mappedBy = "movie")
    private List<Rating> ratings;

    //contructor 
    public Movie(int id, String title,
    int year, String genres, String director, 
    String country, int minutes, String poster) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genres = genres;
        this.director = director;
        this.country = country;
        this.minutes = minutes;
        this.poster = poster;
    }

    public Movie(String title, int year, String genres, String director, String country, int minutes, String poster) {
        this.title = title;
        this.year = year;
        this.genres = genres;
        this.director = director;
        this.country = country;
        this.minutes = minutes;
        this.poster = poster;
    }
}
