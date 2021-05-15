package es.animal.protection.animalshelter.infrastructure.api.resources;

import es.animal.protection.animalshelter.domain.model.Cat;
import es.animal.protection.animalshelter.domain.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(CatResource.CATS)
public class CatResource {
    public static final String CATS = "/cats";
    public static final String CHIP = "/{chip}";


    private CatService catService;

    @Autowired
    public CatResource(CatService catService) {
        this.catService = catService;
    }

    @PostMapping(produces = {"application/json"})
    Mono<Cat> create(@Valid @RequestBody Cat cat) {
        return this.catService.create(cat);
    }
    @GetMapping(CHIP)
    Mono<Cat> read(@PathVariable Integer chip) {
        return this.catService.read(chip);
    }

    @PutMapping(CHIP)
    Mono<Cat> update(@PathVariable Integer chip, @Valid @RequestBody Cat cat) {
        return this.catService.update(chip, cat);
    }
    @DeleteMapping(CHIP)
    Mono<Void> delete(@PathVariable Integer chip) {
        return this.catService.delete(chip);
    }

}
