package es.animal.protection.animalshelter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnimalShelterApplicationTests {

	@Test
	void contextLoads() {
		String hello = "Hello Worlds";
		HelloWorld helloWorld = new HelloWorld();
		Assertions.assertEquals(hello, helloWorld.home());
	}

}
