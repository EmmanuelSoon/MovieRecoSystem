package YCEM.MovieRecoSystem.repo;

import YCEM.MovieRecoSystem.model.Rater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaterRepository extends JpaRepository<Rater, Integer> {

    Boolean existsRaterById(int id);
}
