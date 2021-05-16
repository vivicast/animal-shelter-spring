package es.animal.protection.animalshelter.infrastructure.mongodb.persistence;

import es.animal.protection.animalshelter.domain.exceptions.ConflictException;
import es.animal.protection.animalshelter.domain.exceptions.NotFoundException;
import es.animal.protection.animalshelter.domain.model.Cat;
import es.animal.protection.animalshelter.domain.persistence.CatPersistence;
import es.animal.protection.animalshelter.infrastructure.mongodb.daos.AdopterReactive;
import es.animal.protection.animalshelter.infrastructure.mongodb.daos.CatReactive;
import es.animal.protection.animalshelter.infrastructure.mongodb.daos.ColonyReactive;
import es.animal.protection.animalshelter.infrastructure.mongodb.entities.CatEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CatPersistenceMongodb implements CatPersistence {

    private CatReactive catReactive;
    private AdopterReactive adopterReactive;
    private ColonyReactive colonyReactive;

    @Autowired
    public CatPersistenceMongodb(CatReactive catReactive, ColonyReactive colonyReactive, AdopterReactive adopterReactive) {
        this.catReactive = catReactive;
        this.adopterReactive =adopterReactive;
        this.colonyReactive = colonyReactive;
    }

    @Override
    public Mono<Cat> create(Cat cat) {
        return this.assertCatNotExist(cat.getChip())
                .then(this.catReactive.save(new CatEntity(cat))
                        .flatMap(catEntity -> Mono.just(catEntity.toCat())));
    }

    @Override
    public Mono<Cat> read(Integer chip) {
        return this.assertCatExist(chip)
                .flatMap(catEntity -> Mono.just(catEntity.toCat()));
    }

    @Override
    public Mono<Cat> update(Integer chip, Cat cat) {

        if (cat.getAdopterNif() != null) {
            return this.createAdoption(chip, cat);
        } else if (cat.getColonyRegistry() != null) {
            return this.assignColony(chip, cat);
        } else {
            return this.updateCat(chip, cat);
        }
    }

    @Override
    public Mono<Void> delete(Integer chip) {
        return this.assertCatExist(chip)
                .flatMap(catEntity -> this.catReactive.delete(catEntity));
    }

    @Override
    public Flux<Cat> findBySociableIsTrueAndDepartureDateIsNull(boolean onlyAdoptable) {
        if (onlyAdoptable) {
            return this.catReactive.findBySociableIsTrueAndDepartureDateIsNull()
                    .map(catEntity -> catEntity.toCat());
        } else {
            return this.catReactive.findAll()
                    .map(catEntity -> catEntity.toCat());
        }
    }

    private Mono<Cat> updateCat(Integer chip, Cat cat) {
        return this.assertCatExist(chip)
                .flatMap(catEntity -> {
                    CatEntity catEntityUpdate = new CatEntity();
                    BeanUtils.copyProperties(cat, catEntityUpdate);
                    catEntityUpdate.setId(catEntity.getId());
                    return this.catReactive.save(catEntityUpdate);
                }).flatMap(catEnt -> Mono.just(catEnt.toCat()));
    }
    private Mono<Cat> assignColony(Integer chip, Cat cat) {
        Mono<CatEntity> catEntityMono = this.assertCatExist(chip);
        return this.colonyReactive.readByRegistry(cat.getColonyRegistry())
                .switchIfEmpty(Mono.error(
                        new NotFoundException("No exist colony with registry: " + cat.getColonyRegistry())
                ))
                .flatMap(colonyEntity -> {
                    return catEntityMono.map(catEntityActual -> {
                        CatEntity catEntityUpdate = new CatEntity();
                        BeanUtils.copyProperties(cat, catEntityUpdate);
                        catEntityUpdate.setColonyEntity(colonyEntity);
                        catEntityUpdate.setId(catEntityActual.getId());
                        return catEntityUpdate;
                    });
                })
                .flatMap(catEntity -> this.catReactive.save(catEntity))
                .flatMap(catEntitySaved -> Mono.just(catEntitySaved.toCat()));
    }

    private Mono<Cat> createAdoption(Integer chip, Cat cat) {
        Mono<CatEntity> catEntityMono = this.assertCatExist(chip);
        return this.adopterReactive.readByNif(cat.getAdopterNif())
                .switchIfEmpty(Mono.error(
                        new NotFoundException("No exist adopter with nif: " + cat.getAdopterNif())
                ))
                .flatMap(adopterEntity -> {
                    return catEntityMono.map(catEntityActual -> {
                        CatEntity catEntityUpdate = new CatEntity();
                        BeanUtils.copyProperties(cat, catEntityUpdate);
                        catEntityUpdate.setAdopterEntity(adopterEntity);
                        catEntityUpdate.setId(catEntityActual.getId());
                        return catEntityUpdate;
                    });
                })
                .flatMap(catEntity -> this.catReactive.save(catEntity))
                .flatMap(catEntitySaved -> Mono.just(catEntitySaved.toCat()));
    }

    private Mono<Void> assertCatNotExist(Integer chip) {
        return this.catReactive.readByChip(chip)
                .flatMap(catEntity -> Mono.error(
                        new ConflictException("Cat with chip " + chip + " already exists ")
                ));
    }

    private Mono<CatEntity> assertCatExist(Integer chip) {
        return this.catReactive.readByChip(chip)
                .switchIfEmpty(Mono.error(new NotFoundException("Cat with chip " + chip + " not found")));
    }
}
