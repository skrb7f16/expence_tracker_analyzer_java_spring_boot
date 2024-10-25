package com.skrb.expensetracker.services;

import com.skrb.expensetracker.Entity.Model.Role;
import com.skrb.expensetracker.Entity.Model.ExpenseUser;
import com.skrb.expensetracker.Entity.RequestBodies.LoginRequest;
import com.skrb.expensetracker.Entity.RequestBodies.RegisterRequest;
import com.skrb.expensetracker.Entity.Responses.AuthenticationFailedResponse;
import com.skrb.expensetracker.Entity.Responses.AuthenticationResponse;
import com.skrb.expensetracker.Entity.Responses.JsonResponse;
import com.skrb.expensetracker.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  JWTService jwtService;
    @Autowired
    private  AuthenticationManager authenticationManager;
    public JsonResponse register(RegisterRequest request){
        var user= ExpenseUser.builder().first_name(request.getFirst_name()).last_name(request.getLast_name())
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
        System.out.println(request.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
            );
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }


        try {
            ExpenseUser user=userRepository.findByUsername(request.getUsername());
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

    public JsonResponse logout() {
    return AuthenticationResponse.builder().msg("Logout").build();
    }
}
