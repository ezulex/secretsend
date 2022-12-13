package org.ezulex.secretsend.service;

import org.ezulex.secretsend.model.Secret;
import org.ezulex.secretsend.repo.SecretRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Service
public class SecretService {
    private final SecretRepo secretRepo;

    @Autowired
    public SecretService(SecretRepo secretRepo) {
        this.secretRepo = secretRepo;
    }

    public Secret addSecret(Secret secret) {
        secret.setHashPhrase(UUID.randomUUID().toString());

        if (!(secret.getPassword() == null)) {
            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            byte[] passwordHashBytes = digest.digest(secret.getPassword().getBytes(StandardCharsets.UTF_8));
            String passwordHash = Base64.getEncoder().encodeToString(passwordHashBytes);
            secret.setPassword(passwordHash);
        }

        return secretRepo.save(secret);
    }

    public Secret findSecretByHashPhrase(String hashPhrase) {
        return secretRepo.findSecretByHashPhrase(hashPhrase);
    }

    public void deleteSecret(String hashPhrase) {
        secretRepo.deleteSecretByHashPhrase(hashPhrase);
    }
}
