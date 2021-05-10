package es.animal.protection.animalshelter.infrastructure.mongodb.daos;

import es.animal.protection.animalshelter.infrastructure.mongodb.entities.AdopterEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface AdopterReactive extends ReactiveSortingRepository <AdopterEntity, String>{
    Mono<AdopterEntity> readByNif(String nif);
}
