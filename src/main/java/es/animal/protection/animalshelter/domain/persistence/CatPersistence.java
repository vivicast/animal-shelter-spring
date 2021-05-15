package es.animal.protection.animalshelter.domain.persistence;

import es.animal.protection.animalshelter.domain.model.Cat;
import es.animal.protection.animalshelter.infrastructure.api.dtos.AdoptionDto;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CatPersistence {

    Mono<Cat> create(Cat cat);

    Mono<Cat> read(Integer chip);

    Mono<Cat> update(Integer chip, Cat cat);

    Mono<Void> delete(Integer chip);

    Flux<Cat> findBySociableIsTrueAndDepartureDateIsNull(boolean onlyAdoptable);

    Mono<Cat> createAdoption(AdoptionDto adoptionDto);
}
