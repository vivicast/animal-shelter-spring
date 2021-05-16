package es.animal.protection.animalshelter.infrastructure.api.resources;

import es.animal.protection.animalshelter.domain.model.Adopter;
import es.animal.protection.animalshelter.domain.service.AdopterService;
import es.animal.protection.animalshelter.infrastructure.api.dtos.AdopterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(AdopterResource.ADOPTERS)
public class AdopterResource {
    public static final String ADOPTERS = "/adopters";
    public static final String NIF_VAL = "/{nif}";
    public static final String NIF = "/nif";

    private AdopterService adopterService;

    @Autowired
    public AdopterResource(AdopterService adopterService) {
        this.adopterService = adopterService;
    }

    @PostMapping(produces = {"application/json"})
    Mono<Adopter> create(@Valid @RequestBody Adopter adopter) {
        return this.adopterService.create(adopter);
    }

    @GetMapping(NIF_VAL)
    Mono<Adopter> read(@PathVariable String nif) {
        return this.adopterService.read(nif);
    }

    @PutMapping(NIF_VAL)
    Mono<Adopter> update(@PathVariable String nif, @Valid @RequestBody Adopter adopter) {
        return this.adopterService.update(nif, adopter);
    }

    @DeleteMapping(NIF_VAL)
    Mono<Void> delete(@PathVariable String nif) {
        return this.adopterService.delete(nif);
    }

    @GetMapping
    Flux<Adopter> findAll() {
        return this.adopterService.findAll();
    }

    @GetMapping(NIF)
    Mono<AdopterDto> findByNifNullSafe(@RequestParam(required = false) String nif) {
        return this.adopterService.findByNifNullSafe(nif)
                .collectList()
                .map(AdopterDto::new);
    }
}
