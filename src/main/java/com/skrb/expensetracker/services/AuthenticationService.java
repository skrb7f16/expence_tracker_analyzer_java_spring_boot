package com.skrb.expensetracker.services;

import com.skrb.expensetracker.Entity.Model.Role;
import com.skrb.expensetracker.Entity.Model.User;
import com.skrb.expensetracker.Entity.RequestBodies.LoginRequest;
import com.skrb.expensetracker.Entity.RequestBodies.RegisterRequest;
import com.skrb.expensetracker.Entity.Responses.AuthenticationFailedResponse;
import com.skrb.expensetracker.Entity.Responses.AuthenticationResponse;
import com.skrb.expensetracker.Entity.Responses.JsonResponse;
import com.skrb.expensetracker.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    public JsonResponse register(RegisterRequest request){
        var user= User.builder().first_name(request.getFirst_name()).last_name(request.getLast_name())
                .username(request.getUsername()).password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();
        var res=userRepository.findByUsername(user.getUsername());
        if(res!=null){
            return AuthenticationFailedResponse.builder().msg("Username already exists").build();
        }
        userRepository.save(user);
        var jwt=jwtService.GenerateToken(user);
        return AuthenticationResponse.builder().token(jwt).username(user.getUsername()).build();

    }

    public JsonResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );

        try {
            User user=userRepository.findByUsername(request.getUsername());

            if(user!=null){
                var jwt=jwtService.GenerateToken(user);
                return AuthenticationResponse.builder().token(jwt).username(user.getUsername()).build();

            }else{
                return AuthenticationFailedResponse.builder().msg("User not found").build();
            }
        }catch (Exception e){
             System.out.print(e.getMessage());
        }

        return AuthenticationFailedResponse.builder().msg("User not found").build();
    }
}
