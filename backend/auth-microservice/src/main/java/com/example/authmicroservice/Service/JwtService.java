package com.example.authmicroservice.Service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.io.Decoders;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String createToken(Map<String,Object> claims ,String username){
        return Jwts.builder()
                .setClaims(claims) //payload
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() +1000*60*30))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }

    public String generateToken(String username){
        Map<String , Object> claims = new HashMap<>();
        return createToken(claims,username);
    }

    public Jwt<Header, Claims> validateToken(final String token){
        return  Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJwt(token);
    }
}
