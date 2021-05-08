package es.animal.protection.animalshelter;

import es.animal.protection.animalshelter.infrastructure.mongodb.daos.AdopterReactive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnimalShelterApplicationTests {

	@Test
	void contextLoads() {
		String hello = "Hello World";
		HelloWorld helloWorld = new HelloWorld();
		Assertions.assertEquals(hello, helloWorld.home());
	}

}
