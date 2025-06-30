package io.github.aguilasa.quizmanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.sql.init.mode=never")
@ActiveProfiles("test")
class QuizmanagerApplicationTests {

	@Test
	void contextLoads() {
	}

}
