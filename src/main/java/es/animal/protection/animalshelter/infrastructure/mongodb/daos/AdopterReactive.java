package es.animal.protection.animalshelter.infrastructure.mongodb.daos;

import es.animal.protection.animalshelter.infrastructure.mongodb.entities.AdopterEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdopterReactive extends ReactiveSortingRepository<AdopterEntity, String> {
    Mono<AdopterEntity> readByNif(String nif);

    @Query("{$and:[" // allow NULL in NIF
            + "?#{ [0] == null ? {_id : {$ne:null}} : { nif : {$regex:[0], $options: 'i'} } }"
            + "] }")
    Flux<AdopterEntity> findByNifNullSafe(String nif);
}
