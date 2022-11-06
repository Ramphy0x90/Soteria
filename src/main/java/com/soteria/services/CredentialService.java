package com.soteria.services;

import com.soteria.exceptions.credential.CredentialAlreadyExists;
import com.soteria.exceptions.credential.CredentialNotFound;
import com.soteria.models.Credential;
import com.soteria.repositories.CredentialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
}
