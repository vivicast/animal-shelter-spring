package es.animal.protection.animalshelter.domain.persistence;

import es.animal.protection.animalshelter.domain.model.Cat;
import es.animal.protection.animalshelter.domain.model.Colony;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CatPersistence {

    Mono<Cat> create(Cat cat);

    Mono<Cat> read(Integer chip);
}
