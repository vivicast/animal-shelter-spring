package es.animal.protection.animalshelter.infrastructure.api.resources;

import es.animal.protection.animalshelter.domain.model.Adopter;
import es.animal.protection.animalshelter.domain.service.AdopterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(AdopterResource.ADOPTERS)
public class AdopterResource {
    public static final String ADOPTERS = "/adopters";
    public static final String NIF = "/{nif}";

    private AdopterService adopterService;

    @Autowired
    public AdopterResource(AdopterService adopterService) {
        this.adopterService = adopterService;
    }
    @PostMapping(produces = {"application/json"})
    Mono<Adopter> create(@Valid @RequestBody Adopter adopter){
        return this.adopterService.create(adopter);
    }

    @GetMapping(NIF)
    Mono<Adopter> read(@PathVariable String nif){
        return this.adopterService.read(nif);
    }

    @PutMapping(NIF)
    Mono<Adopter> update(@PathVariable String nif, @Valid @RequestBody Adopter adopter){
        return this.adopterService.update(nif, adopter);
    }
    @DeleteMapping(NIF)
    Mono<Void> delete(@PathVariable String nif){
        return this.adopterService.delete(nif);
    }

}
