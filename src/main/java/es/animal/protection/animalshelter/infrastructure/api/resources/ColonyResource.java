package es.animal.protection.animalshelter.infrastructure.api.resources;

import es.animal.protection.animalshelter.domain.model.Colony;
import es.animal.protection.animalshelter.domain.service.ColonyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(ColonyResource.COLONIES)
public class ColonyResource {
    public static final String COLONIES = "/colonies";
    public static final String REGISTRY = "/{registry}";

    private ColonyService colonyService;

    @Autowired
    public ColonyResource(ColonyService colonyService) {
        this.colonyService = colonyService;
    }

    @PostMapping(produces = {"application/json"})
    Mono<Colony> create(@Valid @RequestBody Colony colony){
        return this.colonyService.create(colony);
    }

    @GetMapping(REGISTRY)
    Mono<Colony> read(@PathVariable Integer registry){
        return this.colonyService.read(registry);
    }

    @PutMapping(REGISTRY)
    Mono<Colony> update(@PathVariable Integer registry, @Valid @RequestBody Colony colony){
        return this.colonyService.update(registry, colony);
    }
    @DeleteMapping(REGISTRY)
    Mono<Void> delete(@PathVariable Integer registry){
        return this.colonyService.delete(registry);
    }

}
