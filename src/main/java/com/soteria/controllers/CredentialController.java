package com.soteria.controllers;

import com.soteria.models.Credential;
import com.soteria.payload.CredentialDTO;
import com.soteria.security.jwt.JwtTokenProvider;
import com.soteria.services.CredentialService;
import com.soteria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/credential")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public CredentialController(CredentialService credentialService,
                                JwtTokenProvider jwtTokenProvider,
                                UserService userService) {
        this.credentialService = credentialService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @GetMapping(path = "/all")
    public List<Credential> getCredentials(@RequestHeader("Authorization") String token) {
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        return credentialService.getCredentials(userId);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Credential> getCredential(@RequestHeader("Authorization") String token,
                                                    @PathVariable("id") Long credentialId) {
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        return new ResponseEntity<>(credentialService.getCredential(userId, credentialId), HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Credential> addCredential(@RequestHeader("Authorization") String token,
                                                    @RequestBody Credential credential) {
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        return new ResponseEntity<>(credentialService.addCredential(userId, credential), HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Credential> updateCredential(@RequestHeader("Authorization") String token,
                                                       @PathVariable("id") Long credentialId,
                                                       @RequestBody CredentialDTO credential) {
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);

        return new ResponseEntity<>(credentialService.updateCredential(userId, credentialId, credential), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> removeCredential(@RequestHeader("Authorization") String token,
                                              @PathVariable("id") Long credentialId) {
        credentialService.removeCredential(credentialId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/decrypt")
    public ResponseEntity<String> decryptCredentialPassword(@RequestHeader("Authorization") String token,
                                                            @RequestBody String password) {
        Long userId = jwtTokenProvider.getUserIdFromJwt(token);
        String clearPassword = credentialService.decrypt(password, userId);

        return ResponseEntity.ok().body(clearPassword);
    }
}
