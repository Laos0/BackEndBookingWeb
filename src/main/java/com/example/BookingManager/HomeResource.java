package com.example.BookingManager;

import com.example.BookingManager.authentication.AuthenticationRequest;
import com.example.BookingManager.authentication.AuthenticationResponse;
import com.example.BookingManager.consoleTextMod.ColorText;
import com.example.BookingManager.userDetails.MyUserDetailsService;
import com.example.BookingManager.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/")
public class HomeResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @GetMapping(path = "/hello")
    public String testApi(){
        return "<h1>HELLO!</h2>";
    }

    @GetMapping
    public String home(){
        return "<h1>Welcome Guest!</h2>";
    }

    @GetMapping(path = "/admin")
    public String admin(){
        return "<h1>Welcome Admin!</h2>";
    }

    @GetMapping(path ="/user")
    public String user(){
        return "<h1>Welcome User!</h2>";
    }


    // The only endpoint that checks authentication
    // takes in the authentication request and
    // checks for username and password
    // authenticationRequest is the username and password that is being sent
    @PostMapping(path = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{

        System.out.println(ColorText.ANSI_YELLOW
                + "From: HomeResource.java, Method: ReponseEntity, Messsage: this method is hit"
                + ColorText.ANSI_RESET);

        // if authentication fails, we will catch the exception
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword())
            );
        }catch(BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }

        // if authentication is successful, we need to return a jdbp
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


}
