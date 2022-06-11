package YCEM.MovieRecoSystem.movie;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import YCEM.MovieRecoSystem.model.*;
import YCEM.MovieRecoSystem.repo.*;;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class movietest {

	@Autowired
	private MovieRepository Mrepo;

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
}
