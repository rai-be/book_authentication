package com.jtw.controller.dto;

import lombok.Data;

@Data
public class DtoRespose {
    private String token;

    public DtoRespose(String token) {
        this.token = token;
    }
}
