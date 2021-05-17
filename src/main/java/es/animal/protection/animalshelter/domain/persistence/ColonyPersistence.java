package es.animal.protection.animalshelter.domain.persistence;

import es.animal.protection.animalshelter.domain.model.Colony;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ColonyPersistence {

    Mono<Colony> create(Colony colony);

    Mono<Colony> readByRegistry(String registry);

    Mono<Colony> updateByRegistry(String registry, Colony colony);

    Mono<Void> deleteByRegistry(String registry);

    Flux<Colony> findByManagerAndLocationNullSafe(String manager, String location);

    Flux<String> findByRegistryNullSafe(String registry);
}
