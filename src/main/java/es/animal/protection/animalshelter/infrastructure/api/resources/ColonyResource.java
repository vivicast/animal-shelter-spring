package es.animal.protection.animalshelter.infrastructure.api.resources;

import es.animal.protection.animalshelter.domain.model.Colony;
import es.animal.protection.animalshelter.domain.service.ColonyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(ColonyResource.COLONIES)
public class ColonyResource {
    public static final String COLONIES = "/colonies";

    private ColonyService colonyService;

    @Autowired
    public ColonyResource(ColonyService colonyService) {
        this.colonyService = colonyService;
    }

    @PostMapping(produces = {"application/json"})
    Mono<Colony> create(@Valid @RequestBody Colony colony){
        return this.colonyService.create(colony);
    }

}
