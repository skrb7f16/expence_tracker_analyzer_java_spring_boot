package com.skrb.expensetracker.controller;


import com.skrb.expensetracker.Entity.Responses.JsonResponse;
import com.skrb.expensetracker.Entity.RequestBodies.LoginRequest;
import com.skrb.expensetracker.Entity.RequestBodies.RegisterRequest;
import com.skrb.expensetracker.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<JsonResponse> register(
            @RequestBody RegisterRequest request
    ){
        JsonResponse res= authenticationService.register(request);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<JsonResponse> login(
            @RequestBody LoginRequest request
    ){
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
