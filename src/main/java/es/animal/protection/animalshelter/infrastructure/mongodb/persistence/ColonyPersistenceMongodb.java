package es.animal.protection.animalshelter.infrastructure.mongodb.persistence;

import es.animal.protection.animalshelter.domain.exceptions.ConflictException;
import es.animal.protection.animalshelter.domain.exceptions.NotFoundException;
import es.animal.protection.animalshelter.domain.model.Colony;
import es.animal.protection.animalshelter.domain.persistence.ColonyPersistence;
import es.animal.protection.animalshelter.infrastructure.mongodb.daos.ColonyReactive;
import es.animal.protection.animalshelter.infrastructure.mongodb.entities.ColonyEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ColonyPersistenceMongodb implements ColonyPersistence {

    private ColonyReactive colonyReactive;

    @Autowired
    public ColonyPersistenceMongodb(ColonyReactive colonyReactive) {
        this.colonyReactive = colonyReactive;
    }

    @Override
    public Mono<Colony> create(Colony colony) {
        return this.assertColonyNotExist(colony.getRegistry())
                .then(this.colonyReactive.save(new ColonyEntity(colony))
                        .flatMap(colonyEntity -> Mono.just(colonyEntity.toColony())));
    }

    @Override
    public Mono<Colony> readByRegistry(String registry) {
        return this.colonyReactive.readByRegistry(registry)
                .switchIfEmpty(Mono.error(new NotFoundException("Colony with number registry " + registry + " not found")))
                .flatMap(colonyEntity -> Mono.just(colonyEntity.toColony()));
    }

    @Override
    public Mono<Colony> updateByRegistry(String registry, Colony colony) {
        return this.colonyReactive.readByRegistry(registry)
                .switchIfEmpty(Mono.error(new NotFoundException("Colony with number registry " + registry + " not found")))
                .flatMap(colonyEntity -> {
                    ColonyEntity colonyEntityUpdate = new ColonyEntity();
                    BeanUtils.copyProperties(colony, colonyEntityUpdate);
                    colonyEntityUpdate.setId(colonyEntity.getId());
                    return this.colonyReactive.save(colonyEntityUpdate);
                })
                .flatMap(colonyEnt -> Mono.just(colonyEnt.toColony()));
    }

    @Override
    public Mono<Void> deleteByRegistry(String registry) {
        return this.colonyReactive.readByRegistry(registry)
                .switchIfEmpty(Mono.error(new NotFoundException("Colony with number registry " + registry + " not found")))
                .flatMap(adopterEntity -> this.colonyReactive.delete(adopterEntity));

    }

    @Override
    public Flux<Colony> findByManagerAndLocationNullSafe(String manager, String location) {
        return this.colonyReactive.findByManagerAndLocationNullSafe(manager, location)
                .map(ColonyEntity::toColony);
    }

    @Override
    public Flux<String> findByRegistryNullSafe(String registry) {
        return this.colonyReactive.findByRegistryNullSafe(registry)
                .map(ColonyEntity::getRegistry);
    }

    private Mono<Void> assertColonyNotExist(String registryNumber) {
        return this.colonyReactive.readByRegistry(registryNumber)
                .flatMap(adopterEntity -> Mono.error(
                        new ConflictException("Colony with number registry " + registryNumber + " already exists ")
                ));
    }
}
