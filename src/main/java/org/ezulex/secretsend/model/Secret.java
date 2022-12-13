package org.ezulex.secretsend.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Secret implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String secret;
    @Column(nullable = false, updatable = false)
    private String hashPhrase;
    private String password;

    public Secret() {
    }

    public Secret(Long id, String secret, String hashPhrase, String password) {
        this.id = id;
        this.secret = secret;
        this.hashPhrase = hashPhrase;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getHashPhrase() {
        return hashPhrase;
    }

    public void setHashPhrase(String hashPhrase) {
        this.hashPhrase = hashPhrase;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Secret{" +
                "id=" + id +
                ", secret='" + secret + '\'' +
                ", hashPhrase='" + hashPhrase + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
