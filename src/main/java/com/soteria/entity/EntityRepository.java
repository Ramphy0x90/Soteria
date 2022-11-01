package com.soteria.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntityRepository extends JpaRepository<Entity, Long> {
    @Query("SELECT e FROM Entity e WHERE e.name = ?1")
    Optional<Entity> findByName(String name);
}
