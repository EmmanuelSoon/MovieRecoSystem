package YCEM.MovieRecoSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import YCEM.MovieRecoSystem.model.Movie;
import YCEM.MovieRecoSystem.repo.MovieRepository;

@SpringBootApplication
public class MovieRecoSystemApplication {

	@Autowired
	MovieRepository Mrepo;

	public static void main(String[] args) {
		SpringApplication.run(MovieRecoSystemApplication.class, args);
	}

	// @Bean
	// public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
	// 	return args -> {
	// 		Movie m1 = Mrepo.findById(6414).get();
	// 		System.out.println(m1);

	// 	};
	// }

}
