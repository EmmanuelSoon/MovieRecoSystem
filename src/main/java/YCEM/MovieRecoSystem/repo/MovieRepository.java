package YCEM.MovieRecoSystem.repo;

import YCEM.MovieRecoSystem.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
