package com.soteria.repositories;

import com.soteria.models.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
    @Query("SELECT c FROM Credential c WHERE user_id = ?1")
    List<Credential> findCredentialsByUserId(Long userId);

    @Query("SELECT c FROM Credential c WHERE c.id = ?1 AND user_id = ?2")
    Optional<Credential> findCredentialByIdAndUserId(Long id, Long userId);

    @Query("SELECT c FROM Credential c WHERE entity_id = ?1 AND user_id = ?2")
    Optional<Credential> findCredentialByEntityIdAndUserId(Long entityId, Long userId);
}
