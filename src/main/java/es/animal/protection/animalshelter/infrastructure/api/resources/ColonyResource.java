package es.animal.protection.animalshelter.infrastructure.api.resources;

import es.animal.protection.animalshelter.domain.model.Colony;
import es.animal.protection.animalshelter.domain.service.ColonyService;
import es.animal.protection.animalshelter.infrastructure.api.dtos.ColonyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(ColonyResource.COLONIES)
public class ColonyResource {
    public static final String COLONIES = "/colonies";
    public static final String REGISTRY_VAL = "/{registry}";
    public static final String REGISTRY = "/registry";

    private ColonyService colonyService;

    @Autowired
    public ColonyResource(ColonyService colonyService) {
        this.colonyService = colonyService;
    }

    @PostMapping(produces = {"application/json"})
    Mono<Colony> create(@Valid @RequestBody Colony colony) {
        return this.colonyService.create(colony);
    }

    @GetMapping(REGISTRY_VAL)
    Mono<Colony> read(@PathVariable String registry) {
        return this.colonyService.read(registry);
    }

    @PutMapping(REGISTRY_VAL)
    Mono<Colony> update(@PathVariable String registry, @Valid @RequestBody Colony colony) {
        return this.colonyService.update(registry, colony);
    }

    @DeleteMapping(REGISTRY_VAL)
    Mono<Void> delete(@PathVariable String registry) {
        return this.colonyService.delete(registry);
    }

    @GetMapping
    Flux<Colony> findByManagerAndLocationNullSafe(
            @RequestParam(required = false) String manager,
            @RequestParam(required = false) String location) {
        return this.colonyService.findByManagerAndLocationNullSafe(manager, location);
    }

    @GetMapping(REGISTRY)
    Mono<ColonyDto> findByRegistryNullSafe(@RequestParam(required = false) String registry) {
        return this.colonyService.findByRegistryNullSafe(registry)
                .collectList()
                .map(ColonyDto::new);
    }
}
