package es.animal.protection.animalshelter.infrastructure.mongodb.daos;

import es.animal.protection.animalshelter.infrastructure.mongodb.entities.AdopterEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

public interface AdopterReactive extends ReactiveSortingRepository <AdopterEntity, String>{
}
