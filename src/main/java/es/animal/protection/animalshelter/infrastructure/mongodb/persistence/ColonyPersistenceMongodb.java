package es.animal.protection.animalshelter.infrastructure.mongodb.persistence;

import es.animal.protection.animalshelter.domain.exceptions.ConflictException;
import es.animal.protection.animalshelter.domain.exceptions.NotFoundException;
import es.animal.protection.animalshelter.domain.model.Colony;
import es.animal.protection.animalshelter.domain.persistence.ColonyPersistence;
import es.animal.protection.animalshelter.infrastructure.mongodb.daos.ColonyReactive;
import es.animal.protection.animalshelter.infrastructure.mongodb.entities.ColonyEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ColonyPersistenceMongodb implements ColonyPersistence {

    private ColonyReactive colonyReactive;

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
                .switchIfEmpty(Mono.error(new NotFoundException("Colony with number registry "+ registry + " not found" )))
                .flatMap(colonyEntity -> Mono.just(colonyEntity.toColony()));
    }

    private Mono<Void> assertColonyNotExist(String registryNumber) {
        return this.colonyReactive.readByRegistry(registryNumber)
                .flatMap(adopterEntity -> Mono.error(
                        new ConflictException("Colony with number registry "+ registryNumber + " already exists ")
                ));
    }


}
