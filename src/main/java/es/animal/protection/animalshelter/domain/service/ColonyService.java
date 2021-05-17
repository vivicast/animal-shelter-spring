package es.animal.protection.animalshelter.domain.service;

import es.animal.protection.animalshelter.domain.model.Colony;
import es.animal.protection.animalshelter.domain.persistence.ColonyPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ColonyService {

    private ColonyPersistence colonyPersistence;

    @Autowired
    public ColonyService(ColonyPersistence colonyPersistence) {
        this.colonyPersistence = colonyPersistence;
    }

    public Mono<Colony> create(Colony colony) {
        return this.colonyPersistence.create(colony);
    }

    public Mono<Colony> read(String registry) {
        return this.colonyPersistence.readByRegistry(registry);
    }

    public Mono<Colony> update(String registry, Colony colony) {
        return this.colonyPersistence.updateByRegistry(registry, colony);
    }

    public Mono<Void> delete(String registry) {
        return this.colonyPersistence.deleteByRegistry(registry);
    }

    public Flux<Colony> findByManagerAndLocationNullSafe(String manager, String location) {
        return this.colonyPersistence.findByManagerAndLocationNullSafe(manager, location);
    }

    public Flux<String> findByRegistryNullSafe(String registry) {
        return this.colonyPersistence.findByRegistryNullSafe(registry);
    }
}
