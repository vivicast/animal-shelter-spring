package es.animal.protection.animalshelter.infrastructure.mongodb.persistence;

import es.animal.protection.animalshelter.domain.exceptions.ConflictException;
import es.animal.protection.animalshelter.domain.model.Adopter;
import es.animal.protection.animalshelter.domain.persistence.AdopterPersistence;
import es.animal.protection.animalshelter.infrastructure.mongodb.daos.AdopterReactive;
import es.animal.protection.animalshelter.infrastructure.mongodb.entities.AdopterEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class AdopterPersistenceMongodb implements AdopterPersistence {

    private AdopterReactive adopterReactive;

    public AdopterPersistenceMongodb(AdopterReactive adopterReactive) {
        this.adopterReactive = adopterReactive;
    }

    @Override
    public Mono<Adopter> create(Adopter adopter) {
        return this.assertAdopterNotExist(adopter.getNif())
                .then(this.adopterReactive.save(new AdopterEntity(adopter))
                        .flatMap(adopterEntity -> Mono.just(adopterEntity.toAdopter())));
    }

    private Mono<Void> assertAdopterNotExist(String nif) {
        return this.readByNif(nif)
                .flatMap(adopterEntity -> Mono.error(
                        new ConflictException("Adopter with NIF already exists : " + nif)
                ));
    }

    @Override
    public Mono<Adopter> readByNif(String nif) {
        return this.adopterReactive.readByNif(nif)
                .flatMap(adopterEntity -> Mono.just(adopterEntity.toAdopter()));
    }
}
