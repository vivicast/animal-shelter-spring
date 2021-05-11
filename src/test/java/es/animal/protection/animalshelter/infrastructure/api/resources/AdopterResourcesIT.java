package es.animal.protection.animalshelter.infrastructure.api.resources;

import es.animal.protection.animalshelter.domain.model.Adopter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureWebTestClient
class AdopterResourcesIT {

    @Autowired
    private WebTestClient webTestClient;




    @Test
    void testCreate() {
        Adopter adopter =  Adopter.builder().nif("1234567").name("Mercedes Torres").address("Calle Pez").birthDay("1991/07/24").build();
        this.webTestClient
                .post()
                .uri(AdopterResource.ADOPTERS)
                .body(Mono.just(adopter), Adopter.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Adopter.class)
                .value(returnAdopter -> {
                    assertThat("1234567").isEqualTo(returnAdopter.getNif());
                    assertThat("1991/07/24").isEqualTo(returnAdopter.getBirthDay());
                });

        this.webTestClient
                .post()
                .uri(AdopterResource.ADOPTERS)
                .body(Mono.just(adopter), Adopter.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }
}
