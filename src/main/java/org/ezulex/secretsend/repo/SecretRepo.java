package org.ezulex.secretsend.repo;

import org.ezulex.secretsend.model.Secret;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecretRepo extends JpaRepository<Secret, Long> {
    Secret findSecretByHashPhrase(String hashPhrase);

    void deleteSecretByHashPhrase(String hashPhrase);
}
