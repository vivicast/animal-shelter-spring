package es.animal.protection.animalshelter.infrastructure.mongodb.daos;

import es.animal.protection.animalshelter.infrastructure.mongodb.entities.ColonyEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface ColonyReactive extends ReactiveSortingRepository <ColonyEntity, String>{
    Mono<ColonyEntity> readByRegistry(Integer registryNumber);
}
