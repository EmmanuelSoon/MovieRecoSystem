package YCEM.MovieRecoSystem.model;


import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@ToString
@NoArgsConstructor
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movieid")
    private int id;
    private String title;
    private int yearPublished;
    private String genres;
    @Column(length = 500)
    private String director;
    private String country;
    private int minutes;
    private String poster;

    //contructor
    public Movie(int id, String title, int yearPublished, String genres, String director, String country, int minutes, String poster) {
        this.id = id;
        this.title = title;
        this.yearPublished = yearPublished;
        this.genres = genres;
        this.director = director;
        this.country = country;
        this.minutes = minutes;
        this.poster = poster;
    }

    public Movie(String title, int yearPublished, String genres, String director, String country, int minutes, String poster) {
        this.title = title;
        this.yearPublished = yearPublished;
        this.genres = genres;
        this.director = director;
        this.country = country;
        this.minutes = minutes;
        this.poster = poster;
    }
}
