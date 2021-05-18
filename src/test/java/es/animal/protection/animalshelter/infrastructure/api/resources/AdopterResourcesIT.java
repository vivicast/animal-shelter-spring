package es.animal.protection.animalshelter.infrastructure.api.resources;

import es.animal.protection.animalshelter.domain.model.Adopter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RestTestConfig
class AdopterResourcesIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateAdopterMary() {
        Adopter adopter = Adopter.builder().nif("0000009A").name("Mary Smith").address("Av. Hollywood").birthDay("1989/05/24").build();
        this.webTestClient
                .post()
                .uri(AdopterResource.ADOPTERS)
                .body(Mono.just(adopter), Adopter.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Adopter.class)
                .value(returnAdopter -> {
                    assertThat("0000009A").isEqualTo(returnAdopter.getNif());
                    assertThat("1989/05/24").isEqualTo(returnAdopter.getBirthDay());
                });

        this.webTestClient
                .post()
                .uri(AdopterResource.ADOPTERS)
                .body(Mono.just(adopter), Adopter.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testRead() {
        Adopter adopter = Adopter.builder().nif("0000008C").name("John Smith").address("Av. Hilton").birthDay("1999/05/24").build();
        this.webTestClient
                .post()
                .uri(AdopterResource.ADOPTERS)
                .body(Mono.just(adopter), Adopter.class)
                .exchange()
                .expectStatus().isOk();
        this.webTestClient
                .get()
                .uri(AdopterResource.ADOPTERS + AdopterResource.NIF_VAL, "0000008C")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Adopter.class)
                .value(returnAdopter -> {
                    assertThat("0000008C").isEqualTo(returnAdopter.getNif());
                    assertThat("1999/05/24").isEqualTo(returnAdopter.getBirthDay());
                    assertThat("John Smith").isEqualTo(returnAdopter.getName());
                    assertThat("Av. Hilton").isEqualTo(returnAdopter.getAddress());
                });
    }

    @Test
    void testUpdate() {
        Adopter adopter = Adopter.builder().nif("0000007C").name("Pauler Smith").address("Av. Hilton").birthDay("1999/05/24").build();
        this.webTestClient
                .post()
                .uri(AdopterResource.ADOPTERS)
                .body(Mono.just(adopter), Adopter.class)
                .exchange()
                .expectStatus().isOk();

        Adopter adopterUpdate = Adopter.builder().nif("0000007C").name("Paul Smith").address("Av. Hilton").birthDay("1999/05/24").build();

        this.webTestClient
                .put()
                .uri(AdopterResource.ADOPTERS + AdopterResource.NIF_VAL, "0000007C")
                .body(Mono.just(adopterUpdate), Adopter.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Adopter.class)
                .value(returnAdopter -> {
                    assertThat("Paul Smith").isEqualTo(returnAdopter.getName());
                });
    }

    @Test
    void testDelete() {
        Adopter adopter = Adopter.builder().nif("0000006C").name("Brad James").address("Av. Hilton").birthDay("1999/05/24").build();
        this.webTestClient
                .post()
                .uri(AdopterResource.ADOPTERS)
                .body(Mono.just(adopter), Adopter.class)
                .exchange()
                .expectStatus().isOk();

        this.webTestClient
                .delete()
                .uri(AdopterResource.ADOPTERS + AdopterResource.NIF_VAL, "0000006C")
                .exchange()
                .expectStatus().isOk();

        this.webTestClient
                .get()
                .uri(AdopterResource.ADOPTERS + AdopterResource.NIF_VAL, "0000006C")
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    void testFindAll() {
        this.webTestClient
                .get()
                .uri(AdopterResource.ADOPTERS)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Adopter.class)
                .value(adopters -> assertTrue(adopters.stream().allMatch(
                        adopter -> adopter.getNif() != null
                )));
    }
}
