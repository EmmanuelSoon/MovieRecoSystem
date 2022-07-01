package YCEM.MovieRecoSystem.movie;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;


import YCEM.MovieRecoSystem.model.*;
import YCEM.MovieRecoSystem.repo.*;
import YCEM.MovieRecoSystem.service.CSVservice;


@ExtendWith(SpringExtension.class)
@Import(CSVservice.class)
@DataJpaTest
public class movietest {

    @Autowired
    CSVservice csvService;

	@Autowired
	private MovieRepository Mrepo;

    @Autowired
	private RaterRepository Rrepo;

    @Test
	@Order(1)
	public void findMovie() {
        //movie id is 6414
		Optional<Movie> m1 = Mrepo.findById(6414);
		Assertions.assertTrue(m1.isPresent());
	}

    @Test
    @Order(2)
    public void modifyMovie(){
        Movie m1 = Mrepo.findById(6414).get();
        if(m1 != null){
            m1.setPoster(null);
            Mrepo.save(m1);
            Movie m2 = Mrepo.findById(6414).get();
            Assertions.assertNull(m2.getPoster());
        }
    }

    @Test
    @Order(3)
    public void externalAPICall(){
        WebClient webClient = WebClient.create("https://api.themoviedb.org/3");

        SearchResult response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/search/movie")
                .queryParam("api_key", "c39d7f9fe2d8ee821511b5f3d66d45c7")
                .queryParam("query", "To Kill a Mockingbird")
                .build())
                .retrieve()
                .bodyToMono(SearchResult.class).block();

        System.out.println(response.results[0].poster_path);
    }

    @Test
    @Order(4)
    public void raterRatingListTest(){
        Rater rater = Rrepo.getReferenceById(1);
        Assertions.assertTrue(!rater.getRatings().isEmpty());
    }
}
