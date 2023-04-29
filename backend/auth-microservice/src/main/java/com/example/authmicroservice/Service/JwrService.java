package com.example.authmicroservice.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwrService {
    @Value("${jwt.secret}")
    private String secret;

    private Key getSignInKey(){
        byte[] keyBytes = Base64.getDecoder().decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }
    private String createToken(Map<String,Object> claims ,String username){
        return Jwts.builder()
                .setClaims(claims) //payload
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() +1000*60*30))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    private String generateToken(String username){
        Map<String , Object> claims = new HashMap<>();
        return createToken(claims,username);
    }

    private Jwt<Header, Claims> validateToken(final String token){
        return  Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJwt(token);
    }
}
