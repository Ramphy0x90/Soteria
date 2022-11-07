package com.soteria.controllers;

import com.soteria.models.Credential;
import com.soteria.models.User;
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
        String userName = jwtTokenProvider.getUserNameFromJwt(token);
        User user = userService.getUser(userName);
        Long userId = user.getId();

        return credentialService.getCredentials(userId);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Credential> getCredential(@RequestHeader("Authorization") String token,
                                                    @PathVariable("id") Long credentialId) {
        String userName = jwtTokenProvider.getUserNameFromJwt(token);
        User user = userService.getUser(userName);
        Long userId = user.getId();

        return new ResponseEntity<>(credentialService.getCredential(userId, credentialId), HttpStatus.OK);
    }
}
