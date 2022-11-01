package com.soteria.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/entity")
public class EntityController {
    private final EntityService entityService;

    @Autowired
    public EntityController(EntityService entityService) {
        this.entityService = entityService;
    }

    @GetMapping
    public List<Entity> getEntities() {
        return entityService.getEntities();
    }

    @PostMapping
    public void insertEntity(@RequestBody Entity entity) {
        entityService.addEntity(entity);
    }

    @DeleteMapping(path = "{entityId}")
    public void deleteEntity(@PathVariable("entityId") long entityId) {
        entityService.removeEntity(entityId);
    }

    @PutMapping(path = "{entityId}")
    public void updateEntity(@PathVariable("entityId") long entityId,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String url,
                             @RequestParam(required = false) String icon) {

    }
}
