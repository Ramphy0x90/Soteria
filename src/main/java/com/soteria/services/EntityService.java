package com.soteria.services;

import com.soteria.models.Entity;
import com.soteria.repositories.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntityService {
    private final EntityRepository entityRepository;

    @Autowired
    public EntityService(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public List<Entity> getEntities() {
        return entityRepository.findAll();
    }

    public void addEntity(Entity entity) {
        Optional<Entity> searchEntity = entityRepository.findByName(entity.getName());

        if(searchEntity.isPresent()) {
            throw new IllegalStateException("Entity already exists");
        }

        entityRepository.save(entity);
    }

    public void updateEntity() {

    }

    public void removeEntity(Long entityId) {
        boolean checkEntity = entityRepository.existsById(entityId);

        if(!checkEntity) {
            throw new IllegalStateException(String.format("Entity with ID: % do not exists", entityId));
        }

        entityRepository.deleteById(entityId);
    }
}
