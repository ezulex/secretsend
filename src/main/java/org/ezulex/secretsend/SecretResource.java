package org.ezulex.secretsend;

import org.ezulex.secretsend.model.Secret;
import org.ezulex.secretsend.service.SecretService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


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
        return new ResponseEntity<>(secret, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{hashPhrase}")
    @Transactional
    public ResponseEntity<?> deleteSecret(@PathVariable("hashPhrase") String hashPhrase) {
        secretService.deleteSecret(hashPhrase);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
