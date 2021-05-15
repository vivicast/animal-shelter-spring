package es.animal.protection.animalshelter.infrastructure.mongodb.daos;


import es.animal.protection.animalshelter.infrastructure.mongodb.entities.CatEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CatReactive extends ReactiveSortingRepository<CatEntity, String> {

    Mono<CatEntity> readByChip(Integer chip);


    @Query("{$and:["
            + "{departureDate : null},"
            + "{sociable : true}"
            + "] }")
    Flux<CatEntity> findBySociableIsTrueAndDepartureDateIsNull();
}
