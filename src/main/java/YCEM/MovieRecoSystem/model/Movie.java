package YCEM.MovieRecoSystem.model;


import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@ToString
@NoArgsConstructor
@Table(name = "movie")
public class Movie {
    @Id
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

    public boolean addPoster() {
        WebClient webClient = WebClient.create("https://api.themoviedb.org/3");
        SearchResult response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                .path("/search/movie")
                .queryParam("api_key", "c39d7f9fe2d8ee821511b5f3d66d45c7")
                .queryParam("query", getTitle())
                .build())
                .retrieve()
                .bodyToMono(SearchResult.class).block();

        for (Result result : response.results){
            String searchedTitle = result.title;
            if (searchedTitle.toLowerCase().equals(getTitle().toLowerCase())){
                setPoster(result.poster_path);
                return true;
            }
        }
        setPoster("/images/notFound.png");
        return  false;
    }
}
