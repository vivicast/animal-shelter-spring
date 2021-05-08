package es.animal.protection.animalshelter;

import es.animal.protection.animalshelter.infrastructure.mongodb.daos.AdopterReactive;
import es.animal.protection.animalshelter.infrastructure.mongodb.entities.AdopterEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class AnimalShelterApplication {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {

        SpringApplication.run(AnimalShelterApplication.class, args);
    }

    /**
     * Usamos CommandLineRunner para iniciar nuestro flujo de trabajo.
     * El CommandLineRunner es una interfaz call back en Spring Boot,
     * cuando nuestra aplicación arranca llamará al método start y le
     * pasará los argumentos unsando el método interno run()
     */
    @Bean
    CommandLineRunner start(AdopterReactive adopterReactive) {
        return args -> {

            Flux.just(
                    AdopterEntity.builder().name("Mary Smith").address("Av. Glory").birthDay("1999/12/09").nif("50645263F").build(),
                    AdopterEntity.builder().name("John Smith").address("Av. Hollywood").birthDay("1995/28/01").nif("000000F").build()
            )
                    .flatMap(adopterReactive::save)
                    .subscribe(person -> log.info("person: {}", person));

        };
    }

}
