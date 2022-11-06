package com.soteria.controllers;

import com.soteria.models.Credential;
import com.soteria.services.CredentialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/credential")
public class CredentialController {
    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @GetMapping(path = "/all")
    public List<Credential> getCredentials() {
        return credentialService.getCredentials(1L);
    }
}
