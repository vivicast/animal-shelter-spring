package es.animal.protection.animalshelter.infrastructure.api.resources;

import es.animal.protection.animalshelter.domain.model.Adopter;
import es.animal.protection.animalshelter.domain.model.Cat;
import es.animal.protection.animalshelter.infrastructure.api.dtos.AdoptionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureWebTestClient
class CatResourcesIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateCat() {
        Cat cat = Cat.builder().chip(1).name("Gimli").admissionDate("2021-01-02").departureDate("2021-05-08").sociable(true).build();
        this.webTestClient
                .post()
                .uri(CatResource.CATS)
                .body(Mono.just(cat), Cat.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cat.class)
                .value(returnCat -> {
                    assertThat(1).isEqualTo(returnCat.getChip());
                    assertThat("Gimli").isEqualTo(returnCat.getName());
                });

        this.webTestClient
                .post()
                .uri(CatResource.CATS)
                .body(Mono.just(cat), Cat.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testRead() {
        Cat cat = Cat.builder().chip(2).name("Arwen").admissionDate("2020-01-02").sociable(true).departureDate("2021-05-08").build();
        this.webTestClient
                .post()
                .uri(CatResource.CATS)
                .body(Mono.just(cat), Cat.class)
                .exchange()
                .expectStatus().isOk();

        this.webTestClient
                .get()
                .uri(CatResource.CATS+ CatResource.CHIP, "2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cat.class)
                .value(returnCat -> {
                    assertThat(2).isEqualTo(returnCat.getChip());
                    assertThat(true).isEqualTo(returnCat.getSociable());
                });

        this.webTestClient
                .get()
                .uri(CatResource.CATS+ CatResource.CHIP, "3")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdate() {
        Cat cat = Cat.builder().chip(3).name("Peke").admissionDate("2021-01-08").sociable(true).departureDate("2021-05-08").build();
        this.webTestClient
                .post()
                .uri(CatResource.CATS)
                .body(Mono.just(cat), Cat.class)
                .exchange()
                .expectStatus().isOk();

        Cat catUpdate = Cat.builder().chip(3).name("Peke").admissionDate("2021-15-08").sociable(true).departureDate("2021-05-08").build();

        this.webTestClient
                .put()
                .uri(CatResource.CATS+ CatResource.CHIP, "3")
                .body(Mono.just(catUpdate), Cat.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cat.class)
                .value(returnCat -> {
                    assertThat("2021-15-08").isEqualTo(returnCat.getAdmissionDate());
                });

        this.webTestClient
                .put()
                .uri(CatResource.CATS+ CatResource.CHIP, "4")
                .body(Mono.just(catUpdate), Cat.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDelete() {
        Cat cat = Cat.builder().chip(4).name("Olivia").admissionDate("2018-05-08").departureDate("2021-05-08").sociable(true).build();
        this.webTestClient
                .post()
                .uri(CatResource.CATS)
                .body(Mono.just(cat), Cat.class)
                .exchange()
                .expectStatus().isOk();

        this.webTestClient
                .delete()
                .uri(CatResource.CATS+ CatResource.CHIP, "4")
                .exchange()
                .expectStatus().isOk();

        this.webTestClient
                .get()
                .uri(CatResource.CATS+ CatResource.CHIP, "4")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testFindBySociableIsTrueAndDepartureDateIsNull() {
        Cat cat1 = Cat.builder().chip(5).name("Aslan").admissionDate("2017-05-08").sociable(true).build();
        Cat cat2 = Cat.builder().chip(6).name("Bud").admissionDate("2018-05-08").sociable(false).build();
        Cat cat3 = Cat.builder().chip(7).name("Milk").admissionDate("2016-05-08").sociable(true).build();

        this.webTestClient
                .post()
                .uri(CatResource.CATS)
                .body(Mono.just(cat1), Cat.class)
                .exchange()
                .expectStatus().isOk();

        this.webTestClient
                .post()
                .uri(CatResource.CATS)
                .body(Mono.just(cat2), Cat.class)
                .exchange()
                .expectStatus().isOk();

        this.webTestClient
                .post()
                .uri(CatResource.CATS)
                .body(Mono.just(cat3), Cat.class)
                .exchange()
                .expectStatus().isOk();

        this.webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(CatResource.CATS)
                        .queryParam("onlyAdoptable", true)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Cat.class)
                .value(cats -> assertTrue(cats.stream()
                        .allMatch(catsAdoptables -> catsAdoptables.getSociable().equals(true)
                                && catsAdoptables.getDepartureDate() == null
                )));
    }

    @Test
    void testCreateAdoption() {
        Cat cat = Cat.builder().chip(8).name("Fenix").admissionDate("2021-01-02").sociable(true).build();
        this.webTestClient
                .post()
                .uri(CatResource.CATS)
                .body(Mono.just(cat), Cat.class)
                .exchange()
                .expectStatus().isOk();

        Adopter adopter = Adopter.builder().nif("5555").name("Mary Smith").address("Av. Hollywood").birthDay("1989/05/24").build();
        this.webTestClient
                .post()
                .uri(AdopterResource.ADOPTERS)
                .body(Mono.just(adopter), Adopter.class)
                .exchange()
                .expectStatus().isOk();

        AdoptionDto adoptionDto = AdoptionDto.builder().chip(8).nifAdopter("5555").build();
        this.webTestClient
                .post()
                .uri(CatResource.CATS+CatResource.ADOPTION)
                .body(Mono.just(adoptionDto), AdoptionDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Cat.class)
                .value(returnCat -> {
                    assertThat("5555").isEqualTo(returnCat.getNifAdopter());
                    assertNotNull(returnCat.getDepartureDate());
                });

    }


}
