package com.soteria.services;

import com.soteria.exceptions.credential.CredentialAlreadyExists;
import com.soteria.exceptions.credential.CredentialNotFound;
import com.soteria.models.Credential;
import com.soteria.repositories.CredentialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CredentialService {
    private final CredentialRepository credentialRepository;

    public CredentialService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    public List<Credential> getCredentials(Long userId) {
        return (List<Credential>) credentialRepository.findCredentialsByUserId(userId);
    }

    public Credential getCredential(Long userId, Long credentialId) {
        return credentialRepository.findCredentialByIdAndUserId(userId, credentialId)
                .orElseThrow(() -> new CredentialNotFound("Credential not found"));
    }

    public Credential addCredential(Long userId, Credential credential) {
        Optional<Credential> checkCredentialExists = credentialRepository.findCredentialByEntityIdAndUserId(
                credential.getEntity().getId(),
                userId
        );

        if(checkCredentialExists.isPresent()) {
            throw new CredentialAlreadyExists("Credential for this entity already exists");
        }

        return credentialRepository.save(credential);
    }

    public Credential updateCredential(Long userId, Credential credential) {
        Credential updatedCredential = credentialRepository.findCredentialByEntityIdAndUserId(
                credential.getEntity().getId(),
                userId
        ).orElseThrow(() -> new CredentialNotFound("Credential not found"));

        updatedCredential.setUserName(credential.getUserName());
        updatedCredential.setPassword(credential.getPassword());

        return updatedCredential;
    }

    public void removeCredential(Long id) {
        credentialRepository.findById(id).orElseThrow(() -> new CredentialNotFound("Credential not found"));
        credentialRepository.deleteById(id);
    }
}
