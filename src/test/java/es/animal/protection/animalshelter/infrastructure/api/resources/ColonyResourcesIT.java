package es.animal.protection.animalshelter.infrastructure.api.resources;

import es.animal.protection.animalshelter.domain.model.Adopter;
import es.animal.protection.animalshelter.domain.model.Colony;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureWebTestClient
class ColonyResourcesIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateColony() {
        Colony colony = Colony.builder().manager("mary").location("Av. Sol").registry("001").build();
        this.webTestClient
                .post()
                .uri(ColonyResource.COLONIES)
                .body(Mono.just(colony), Adopter.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Colony.class)
                .value(returnColony -> {
                    assertThat("001").isEqualTo(returnColony.getRegistry());
                    assertThat("mary").isEqualTo(returnColony.getManager());
                });

        this.webTestClient
                .post()
                .uri(ColonyResource.COLONIES)
                .body(Mono.just(colony), Colony.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testRead() {
        Colony colony = Colony.builder().manager("Jhon").location("Av. Tree").registry("002").build();
        this.webTestClient
                .post()
                .uri(ColonyResource.COLONIES)
                .body(Mono.just(colony), Adopter.class)
                .exchange()
                .expectStatus().isOk();

        this.webTestClient
                .get()
                .uri(ColonyResource.COLONIES + ColonyResource.REGISTRY_VAL, "002")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Colony.class)
                .value(returnColony -> {
                    assertThat("002").isEqualTo(returnColony.getRegistry());
                    assertThat("Jhon").isEqualTo(returnColony.getManager());
                });

        this.webTestClient
                .get()
                .uri(ColonyResource.COLONIES + ColonyResource.REGISTRY_VAL, "003")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testUpdate() {
        Colony colony = Colony.builder().manager("Helen").location("Av. Sky").registry("003").build();

        this.webTestClient
                .post()
                .uri(ColonyResource.COLONIES)
                .body(Mono.just(colony), Colony.class)
                .exchange()
                .expectStatus().isOk();

        Colony colonyUpdate = Colony.builder().manager("Chris Wall").location("Av. Sky").registry("003").build();

        this.webTestClient
                .put()
                .uri(ColonyResource.COLONIES + ColonyResource.REGISTRY_VAL, "003")
                .body(Mono.just(colonyUpdate), Colony.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Colony.class)
                .value(returnColony -> {
                    assertThat("Chris Wall").isEqualTo(returnColony.getManager());
                    assertThat("003").isEqualTo(returnColony.getRegistry());
                });

        this.webTestClient
                .put()
                .uri(ColonyResource.COLONIES + ColonyResource.REGISTRY_VAL, "004")
                .body(Mono.just(colonyUpdate), Colony.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDelete() {
        Colony colony = Colony.builder().manager("Helen").location("Av. Sky").registry("004").build();
        this.webTestClient
                .post()
                .uri(ColonyResource.COLONIES)
                .body(Mono.just(colony), Colony.class)
                .exchange()
                .expectStatus().isOk();

        this.webTestClient
                .delete()
                .uri(ColonyResource.COLONIES + ColonyResource.REGISTRY_VAL, "004")
                .exchange()
                .expectStatus().isOk();

        this.webTestClient
                .get()
                .uri(ColonyResource.COLONIES + ColonyResource.REGISTRY_VAL, "004")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testFindByManagerAndLocationNullSafe() {
        Colony colony1 = Colony.builder().manager("Sam").location("Av. Green").registry("005").build();
        Colony colony2 = Colony.builder().manager("Sam").location("Av. Green").registry("006").build();

        this.webTestClient
                .post()
                .uri(ColonyResource.COLONIES)
                .body(Mono.just(colony1), Colony.class)
                .exchange()
                .expectStatus().isOk();

        this.webTestClient
                .post()
                .uri(ColonyResource.COLONIES)
                .body(Mono.just(colony2), Colony.class)
                .exchange()
                .expectStatus().isOk();

        this.webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(ColonyResource.COLONIES)
                        .queryParam("manager", "Sam")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Colony.class)
                .value(colonies -> assertTrue(colonies.stream().allMatch(
                        cols -> cols.getManager().equals("Sam")
                )));
    }
}
