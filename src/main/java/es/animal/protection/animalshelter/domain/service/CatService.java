package es.animal.protection.animalshelter.domain.service;

import es.animal.protection.animalshelter.domain.model.Cat;
import es.animal.protection.animalshelter.domain.model.Colony;
import es.animal.protection.animalshelter.domain.persistence.CatPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CatService {

    private CatPersistence catPersistence;

    @Autowired
    public CatService(CatPersistence catPersistence) {
        this.catPersistence = catPersistence;
    }

    public Mono<Cat> create(Cat cat){
        return this.catPersistence.create(cat);
    }

    public Mono<Cat> read(Integer chip) {
        return this.catPersistence.read(chip);
    }
}
