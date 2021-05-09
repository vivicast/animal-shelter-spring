package es.animal.protection.animalshelter.infrastructure.api.resources;

import es.animal.protection.animalshelter.domain.model.Adopter;
import es.animal.protection.animalshelter.domain.service.AdopterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(AdopterResource.ADOPTERS)
public class AdopterResource {
    public static final String ADOPTERS = "/adopters";

    private AdopterService adopterService;

    @Autowired
    public AdopterResource(AdopterService adopterService) {
        this.adopterService = adopterService;
    }
    @PostMapping(produces = {"application/json"})
    Mono<Adopter> create(@RequestBody Adopter adopter){
        return this.adopterService.create(adopter);
    }
}
