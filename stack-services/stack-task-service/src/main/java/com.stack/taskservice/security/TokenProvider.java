package com.stack.taskservice.security;

import com.stack.taskservice.configuration.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private AppProperties appProperties;


    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(
                now.getTime() + appProperties.getTokenExpirationMsec());


        return Jwts.builder()
                   .setSubject(userPrincipal.getEmail())
                   .setIssuedAt(new Date())
                   .setExpiration(expiryDate)
                   .signWith(SignatureAlgorithm.HS512, appProperties.getTokenSecret())
                   .compact();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(appProperties.getTokenSecret())
                            .parseClaimsJws(token)
                            .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        Jwts.parser().setSigningKey(appProperties.getTokenSecret()).parseClaimsJws(
                authToken);
        return true;
    }

}
