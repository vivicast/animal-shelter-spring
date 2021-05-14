package es.animal.protection.animalshelter.infrastructure.mongodb.daos;

import es.animal.protection.animalshelter.infrastructure.mongodb.entities.ColonyEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ColonyReactive extends ReactiveSortingRepository <ColonyEntity, String>{
    Mono<ColonyEntity> readByRegistry(Integer registryNumber);

    @Query("{$and:[" // allow NULL: all elements
            + "?#{ [0] == null ? {_id : {$ne:null}} : { manager : {$regex:[0], $options: 'i'} } },"
            + "?#{ [1] == null ? {_id : {$ne:null}} : { location : {$regex:[1], $options: 'i'} } }"
            + "] }")
    Flux<ColonyEntity> findByManagerAndLocationNullSafe(String manager, String location);
}
