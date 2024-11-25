package com.jtw.controller;

import com.jtw.controller.dto.DtoRequest;
import com.jtw.controller.dto.DtoRespose;
import com.jtw.service.ServiceAuth;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class ControllerAuth {

    private final ServiceAuth authService;

    public ControllerAuth(ServiceAuth authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public DtoRespose login(@RequestBody DtoRequest dtoRequest) {
        return authService.authenticate(dtoRequest);
    }

}
