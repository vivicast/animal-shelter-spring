package es.animal.protection.animalshelter.domain.persistence;

import es.animal.protection.animalshelter.domain.model.Adopter;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AdopterPersistence {

    Mono<Adopter> create(Adopter adopter);
    Mono<Adopter> readByNif(String nif);

}
