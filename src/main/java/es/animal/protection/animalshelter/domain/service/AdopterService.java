package es.animal.protection.animalshelter.domain.service;

import es.animal.protection.animalshelter.domain.exceptions.NotFoundException;
import es.animal.protection.animalshelter.domain.model.Adopter;
import es.animal.protection.animalshelter.domain.persistence.AdopterPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AdopterService {
    private AdopterPersistence adopterPersistence;

    @Autowired
    public AdopterService(AdopterPersistence adopterPersistence) {
        this.adopterPersistence = adopterPersistence;
    }

    public Mono<Adopter> create(Adopter adopter){
        return this.adopterPersistence.create(adopter);
    }

    public Mono<Adopter> read(String nif) {
        return this.adopterPersistence.readByNif(nif);
    }

    public Mono<Adopter> update(String nif, Adopter adopter) {
        return this.adopterPersistence.updateByNif(nif, adopter);

    }
}
