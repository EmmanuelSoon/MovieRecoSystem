package model;


import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Movie {
    private String id;
    private String title;
    private int year;
    private String genres;
    private String director;
    private String country;
    private int minutes;
    private String poster;

    //contructor 
    public Movie(String id, String title, 
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


    @Override
    public String toString(){
        return "ID: " + this.id + ", Title: " + this.title + 
        ", Year: " + this.year +
        ", Country: " + this.country +
        ", Genres: " + this.genres +
        ", Directed by: " + this.director +
        ", Duration: " + this.minutes +
        ", Posted at: " + this.poster;
    }

}
