package es.animal.protection.animalshelter.infrastructure.mongodb.daos;

import es.animal.protection.animalshelter.infrastructure.mongodb.entities.ColonyEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ColonyReactive extends ReactiveSortingRepository <ColonyEntity, String>{
    Mono<ColonyEntity> readByRegistry(String registry);

    @Query("{$and:[" // allow NULL: manager and location
            + "?#{ [0] == null ? {_id : {$ne:null}} : { manager : {$regex:[0], $options: 'i'} } },"
            + "?#{ [1] == null ? {_id : {$ne:null}} : { location : {$regex:[1], $options: 'i'} } }"
            + "] }")
    Flux<ColonyEntity> findByManagerAndLocationNullSafe(String manager, String location);

    @Query("{$and:[" // allow NULL: registry
            + "?#{ [0] == null ? {_id : {$ne:null}} : { registry : {$regex:[0], $options: 'i'} } }"
            + "] }")
    Flux<ColonyEntity> findByRegistryNullSafe(String registry);
}
