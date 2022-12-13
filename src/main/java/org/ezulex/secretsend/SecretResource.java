package org.ezulex.secretsend;

import org.ezulex.secretsend.model.Secret;
import org.ezulex.secretsend.service.SecretService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


@RestController
@RequestMapping("/secret")
public class SecretResource {
    private final SecretService secretService;

    public SecretResource(SecretService secretService) {
        this.secretService = secretService;
    }

    @PostMapping("/add")
    public ResponseEntity<Secret> addEmployee(@RequestBody Secret secret) {
        Secret newSecret = secretService.addSecret(secret);
        return new ResponseEntity<>(newSecret, HttpStatus.CREATED);
    }

    @GetMapping("/{hashPhrase}")
    public ResponseEntity<Secret> getEmployeeById(@PathVariable("hashPhrase") String hashPhrase) {
        Secret secret = secretService.findSecretByHashPhrase(hashPhrase);
        if (secret.getPassword() == null) {
            return new ResponseEntity<>(secret, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{hashPhrase}/{password}")
    public ResponseEntity<Secret> getEmployeeByIdWithPassword(
            @PathVariable("hashPhrase") String hashPhrase,
            @PathVariable("password") String password) {
        Secret secret = secretService.findSecretByHashPhrase(hashPhrase);

        if (secret.getPassword() == null) {
            return new ResponseEntity<>(secret, HttpStatus.OK);
        } else {
            MessageDigest digest = null;
            try {
                digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            byte[] passwordHashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String passwordHash = Base64.getEncoder().encodeToString(passwordHashBytes);

            if (passwordHash.equals(secret.getPassword())) {
                return new ResponseEntity<>(secret, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }
        }
    }

    @DeleteMapping("/delete/{hashPhrase}")
    @Transactional
    public ResponseEntity<?> deleteSecret(@PathVariable("hashPhrase") String hashPhrase) {
        secretService.deleteSecret(hashPhrase);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
