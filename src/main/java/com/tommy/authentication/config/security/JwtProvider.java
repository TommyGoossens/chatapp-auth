package com.tommy.authentication.config.security;

import com.tommy.authentication.model.User;
import com.tommy.authentication.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.tommy.authentication.config.security.SecurityConstants.EXPIRATION_TIME;
import static com.tommy.authentication.config.security.SecurityConstants.SECRET_KEY;

@Component
public class JwtProvider {

    private AuthenticationService service;

    @Autowired
    public JwtProvider(AuthenticationService service){
        this.service = service;
    }

    public String createToken(User user, List<String> roles){
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("roles",roles);

        Date issuedAt = new Date();
        Date expiredAt = new Date(issuedAt.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    /**
     * Consumes a token in String format and determine whether the token is valid or not
     * @param token JWT in String format
     * @return if a token is valid or not
     */
    public boolean validateToken(String token){
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        return true;
    }

    /**
     * Consumes an existing JWT in String format and creates an Authentication object for the user
     * to which the JWT belongs.
     * @param token JWT in String format
     * @return Authentication object of the user to which to JWT belongs.
     */
    public Authentication getAuthentication(String token){
        UserDetails userDetails = service.getUserDetailsByEmail(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * Consumes an existing JWT in String format and retrieves the email address from the JWT.
     * @param token JWT in String format
     * @return The email of the user to which the JWT belongs
     */
    private String getEmail(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
