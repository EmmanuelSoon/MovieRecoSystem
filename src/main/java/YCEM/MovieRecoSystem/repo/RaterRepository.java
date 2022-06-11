package repo;

import model.Rater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaterRepository extends JpaRepository<Rater, Integer> {
}
