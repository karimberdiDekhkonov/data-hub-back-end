package com.datahub.datahub.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component

public class JwtProvider {
    private static final long expireTime = 60 * 60 * 24;
    private static final String secretKey = "Ismigul";


    public String generateToken(String email){
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);

        String token = Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return token;
    }


    public String getEmailFromToken(String token){
        try{
            String email = Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return email;
        }
        catch (Exception e){
            return null;
        }
    }
}
