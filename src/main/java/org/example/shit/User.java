package org.example.shit;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int key;

    @Column(name = "fullname")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "pass")
    private String pass;

    @Column(name = "email")
    private String email;

    @Column(name = "type_of_artist")
    private String artistType;

    // Required by Hibernate (no-arg constructor)
    public User() {
    }

    // Builder constructor
    private User(Builder builder) {
        this.name = builder.name;
        this.username = builder.username;
        this.pass = builder.pass;
        this.email = builder.email;
        this.artistType = builder.artistType;
    }

    // === Getters ===

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public String getType_of_artist() {
        return artistType;
    }

    // === Builder class ===

    public static class Builder {
        private String name, username, pass, email, artistType;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPass(String pass) {
            this.pass = pass;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setArtistType(String artistType) {
            this.artistType = artistType;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
