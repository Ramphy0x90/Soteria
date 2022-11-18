package com.soteria.services;

import com.soteria.exceptions.credential.CredentialAlreadyExists;
import com.soteria.exceptions.credential.CredentialNotFound;
import com.soteria.models.Credential;
import com.soteria.models.User;
import com.soteria.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 *  Credential service
 *  @author  Ramphy Aquino Nova
 *  @version 2022.11.15
 */
@Service
@Transactional
public class CredentialService {
    private final CredentialRepository credentialRepository;
    private final UserService userService;

    @Autowired
    public CredentialService(CredentialRepository credentialRepository, UserService userService) {
        this.credentialRepository = credentialRepository;
        this.userService = userService;
    }

    /**
     * Get all credentials
     * @param userId User id
     * @return List<Credential>
     */
    public List<Credential> getCredentials(Long userId) {
        return credentialRepository.findCredentialsByUserId(userId);
    }

    /**
     * Get credential by user id and credential id
     * @param userId User id
     * @param credentialId Credential id
     * @return Credential
     */
    public Credential getCredential(Long userId, Long credentialId) {
        return credentialRepository.findCredentialByIdAndUserId(userId, credentialId)
                .orElseThrow(() -> new CredentialNotFound("Credential not found"));
    }

    /**
     * Create new credential
     * @param userId User id
     * @param credential New Credential object
     * @return Credential
     */
    public Credential addCredential(Long userId, Credential credential) {
        Optional<Credential> checkCredentialExists = credentialRepository.findCredentialByEntityIdAndUserId(
                credential.getEntity().getId(),
                userId
        );

        if(checkCredentialExists.isPresent()) {
            throw new CredentialAlreadyExists("Credential for this entity already exists");
        }

        User user = userService.getUser(userId);
        credential.setUser(user);

        return credentialRepository.save(credential);
    }

    /**
     * Update credential by give user id and credential
     * @param userId User id
     * @param credential Credential
     * @return Credential
     */
    public Credential updateCredential(Long userId, Credential credential) {
        Credential updatedCredential = credentialRepository.findCredentialByEntityIdAndUserId(
                credential.getEntity().getId(),
                userId
        ).orElseThrow(() -> new CredentialNotFound("Credential not found"));

        updatedCredential.setUserName(credential.getUserName());
        updatedCredential.setPassword(credential.getPassword());

        return updatedCredential;
    }

    /**
     * Delete Credential by given id
     * @param id Credential id
     */
    public void removeCredential(Long id) {
        credentialRepository.findById(id).orElseThrow(() -> new CredentialNotFound("Credential not found"));
        credentialRepository.deleteById(id);
    }
}
