package es.animal.protection.animalshelter.infrastructure.mongodb.persistence;

import es.animal.protection.animalshelter.domain.exceptions.ConflictException;
import es.animal.protection.animalshelter.domain.exceptions.NotFoundException;
import es.animal.protection.animalshelter.domain.model.Adopter;
import es.animal.protection.animalshelter.domain.persistence.AdopterPersistence;
import es.animal.protection.animalshelter.infrastructure.mongodb.daos.AdopterReactive;
import es.animal.protection.animalshelter.infrastructure.mongodb.entities.AdopterEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class AdopterPersistenceMongodb implements AdopterPersistence {

    private AdopterReactive adopterReactive;

    @Autowired
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
        return this.adopterReactive.readByNif(nif)
                .flatMap(adopterEntity -> Mono.error(
                        new ConflictException("Adopter with NIF " + nif + " already exists ")
                ));
    }

    @Override
    public Mono<Adopter> readByNif(String nif) {
        return this.adopterReactive.readByNif(nif)
                .switchIfEmpty(Mono.error(new NotFoundException("Adopter with NIF " + nif + " not found")))
                .flatMap(adopterEntity -> Mono.just(adopterEntity.toAdopter()));
    }

    @Override
    public Mono<Adopter> updateByNif(String nif, Adopter adopter) {
        return this.adopterReactive.readByNif(nif)
                .switchIfEmpty(Mono.error(new NotFoundException("Adopter with NIF " + nif + " not found")))
                .flatMap(adopterEntity -> {
                    AdopterEntity adopterEntityUpdate = new AdopterEntity();
                    BeanUtils.copyProperties(adopter, adopterEntityUpdate);
                    adopterEntityUpdate.setId(adopterEntity.getId());
                    return this.adopterReactive.save(adopterEntityUpdate);
                })
                .flatMap(adopterEnt -> Mono.just(adopterEnt.toAdopter()));
    }

    @Override
    public Mono<Void> deleteByNif(String nif) {
        return this.adopterReactive.readByNif(nif)
                .switchIfEmpty(Mono.error(new NotFoundException("Adopter with NIF " + nif + " not found")))
                .flatMap(adopterEntity -> this.adopterReactive.delete(adopterEntity));
    }

    @Override
    public Flux<Adopter> findAll() {
        return this.adopterReactive.findAll()
                .map(AdopterEntity::toAdopter);
    }

    @Override
    public Flux<String> findByNifNullSafe(String nif) {
        return this.adopterReactive.findByNifNullSafe(nif)
                .map(AdopterEntity::getNif);
    }

}
