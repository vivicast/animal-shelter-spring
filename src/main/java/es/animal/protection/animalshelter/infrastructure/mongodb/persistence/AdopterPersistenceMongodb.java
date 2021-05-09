package es.animal.protection.animalshelter.infrastructure.mongodb.persistence;

import es.animal.protection.animalshelter.domain.model.Adopter;
import es.animal.protection.animalshelter.domain.persistence.AdopterPersistence;
import es.animal.protection.animalshelter.infrastructure.mongodb.daos.AdopterReactive;
import es.animal.protection.animalshelter.infrastructure.mongodb.entities.AdopterEntity;
import reactor.core.publisher.Mono;

public class AdopterPersistenceMongodb implements AdopterPersistence {

    private AdopterReactive adopterReactive;

    public AdopterPersistenceMongodb(AdopterReactive adopterReactive) {
        this.adopterReactive = adopterReactive;
    }

    @Override
    public Mono<Adopter> create(Adopter adopter) {
      return this.adopterReactive.save(new AdopterEntity(adopter))
        .flatMap(adopterEntity -> Mono.just(adopterEntity.toAdopter()));
    }
}
