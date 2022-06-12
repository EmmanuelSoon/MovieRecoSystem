package YCEM.MovieRecoSystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import YCEM.MovieRecoSystem.service.CSVservice;

@SpringBootTest
class MovieRecoSystemApplicationTests {

	@Autowired
	CSVservice fileService;

	@Test
	void contextLoads() {
		fileService.csvInit();
	}

}
