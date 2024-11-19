package com.jtw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    private String username;

    private String password;

    public String getPassword() {
        return null;
    }

    public Object getUsername() {
        return null;
    }
}
