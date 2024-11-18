package com.jtw.controller.dto;

import lombok.Data;

@Data
public class DtoRequest {
    private String username;
    private String password;

    public String getUsername() {
        return "";
    }

    public CharSequence getPassword() {
        return null;
    }
}
