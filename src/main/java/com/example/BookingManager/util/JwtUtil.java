package com.example.BookingManager.util;

import com.example.BookingManager.consoleTextMod.ColorText;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String SECRET_KEY = "secret";
    private String username;

    // takes the token and return the username
    public String extractUsername(String token){

        String g = extractClaim(token, Claims::getSubject);
        return g;
    }

    // takes in token and returns the expiration date
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    // use claimsResolve to figure out what the claims are
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        //System.out.println(ColorText.ANSI_RED + claimsResolver.apply(claims) + ColorText.ANSI_RESET);
        return claimsResolver.apply(claims);
        //return null;
    }

    public Claims extractAllClaims(String token){
        Claims claim = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claim;
    }

    // checking if the token is expired before the current date
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date()); // .before(new Date()) basically means the time right now?
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    // setting the claims, and set subject who is being authenticated(username)
    // issuing the current time and the expiration starting at the current time (10hrs from now)
    // sign in with the token using the algo.
    public String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 300000)) // 1000 * 60 * 60 * 10 = 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    // checks if userame is the same as the username of the userDetails+
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // this one will validate the token without the userDetails
    public Boolean validateIncomingToken(String token){
        System.out.println(token + " " + isTokenExpired(token));
        if(!isTokenExpired(token)){
            return true;
        }
        return false;
    }
}
