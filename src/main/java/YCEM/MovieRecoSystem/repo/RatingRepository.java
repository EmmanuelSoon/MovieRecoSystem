package YCEM.MovieRecoSystem.repo;

import YCEM.MovieRecoSystem.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    @Query("Select r from Rating r where r.movie.id = :id")
    public List<Rating> findAllByMovieId(int id);
}
