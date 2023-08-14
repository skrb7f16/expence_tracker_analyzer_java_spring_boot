package com.skrb.expensetracker.services;

import com.skrb.expensetracker.Entity.*;
import com.skrb.expensetracker.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request){
        var user=User.builder().first_name(request.getFirst_name()).last_name(request.getLast_name())
                .username(request.getUsername()).password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();

        var res=userRepository.save(user);

        var jwt=jwtService.GenerateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }

    public AuthenticationResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );
        var user=userRepository.findByUsername(request.getUsername());
        var jwt=jwtService.GenerateToken(user);
        return AuthenticationResponse.builder().token(jwt).build();
    }
}
