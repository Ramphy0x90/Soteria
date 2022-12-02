package com.soteria.services;

import com.soteria.exceptions.credential.CredentialAlreadyExists;
import com.soteria.exceptions.credential.CredentialNotFound;
import com.soteria.models.Credential;
import com.soteria.models.User;
import com.soteria.payload.CredentialDTO;
import com.soteria.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
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

        try {
            User user = userService.getUser(userId);
            SecretKey secret = getSecretKey(user.getUserName());
            String encryptedPassword = encrypt(credential.getPassword(), secret);
            credential.setUser(user);
            credential.setPassword(encryptedPassword);

            return credentialRepository.save(credential);
        } catch(Exception e) {
            throw new RuntimeException("Unexpected error encrypting credential password\n\n" + e);
        }
    }

    /**
     * Update credential by give user id and credential
     * @param userId User id
     * @param credential Credential
     * @return Credential
     */
    public Credential updateCredential(Long userId, Long credentialId, CredentialDTO credential) {
        Credential updatedCredential = credentialRepository.findById(
                credentialId
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

    /**
     * Generate a secret key for the AES encryption
     * @param userName username
     * @return Secret key
     * @throws InvalidKeySpecException .
     * @throws NoSuchAlgorithmException .
     */
    private SecretKey getSecretKey(String userName) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(userName.toCharArray(), userName.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    /**
     * Encrypt a given input with AES
     * @param input string to encrypt
     * @param key key to encrypt
     * @return input encrypted
     * @throws NoSuchPaddingException .
     * @throws NoSuchAlgorithmException .
     * @throws InvalidAlgorithmParameterException .
     * @throws InvalidKeyException .
     * @throws BadPaddingException .
     * @throws IllegalBlockSizeException .
     */
    public String encrypt(String input, SecretKey key)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    /**
     * Decrypt password by given AES
     * @param cipherText AES string
     * @return Plain text
     */
    public String decrypt(String cipherText, Long userId) {
        try {
            User user = userService.getUser(userId);
            SecretKey secret = getSecretKey(user.getUserName());

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            return new String(plainText);
        } catch(Exception e) {
            throw new RuntimeException("Unexpected error decrypting credential password\n\n" + e);
        }
    }
}
