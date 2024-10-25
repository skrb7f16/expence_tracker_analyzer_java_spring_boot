package com.skrb.expensetracker.services;

import com.skrb.expensetracker.Entity.Model.ExpenseUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService {

    final private static String SECRET_KEY="C8B482BF5A28390AFEC19E09C0B0161A7CE34931C4EF5586EA71ABE7741FB976524405E232EE5B164ED8B4BF3862C36955C8AE071F7E99550C969036566A14E5210E0B82992E7E3D8436729F40BA14859E585CD13639D4121BAF104427FE0DFC8B9DA6AEFB11E68DC652E80F75320E04188FA3EA142E18B208991547CB6B10A6";
    public String extractUserName(String token){
        return getClaim(token,Claims::getSubject);
    }

    public Date extractExpirationDate(String token){
        return getClaim(token,Claims::getExpiration);
    }

    public String GenerateToken(ExpenseUser userDetails){
        HashMap<String, Object> extraClaims=new HashMap<String,Object>();
        extraClaims.put("roles", userDetails.getRole());
        return GenerateToken(extraClaims,userDetails);
    }

    public String GenerateToken(
            Map<String,Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts.builder()
                .addClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000000*60*24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public <T> T getClaim(String token, Function<Claims,T> resolver){
        Claims claims=getAllClaims(token);
        return resolver.apply(claims);
    }

    public Claims getAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSigninKey() {
        byte [] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Boolean isTokenValid(String token,UserDetails userDetails){
        return userDetails.getUsername().equals(this.extractUserName(token)) && !isTokenExpired(token);
    }

    public Boolean isTokenExpired(String token){
        return extractExpirationDate(token).before(new Date(System.currentTimeMillis()));
    }
}
