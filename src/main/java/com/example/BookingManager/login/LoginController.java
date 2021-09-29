package com.example.BookingManager.login;

import com.example.BookingManager.authentication.AuthenticationRequest;
import com.example.BookingManager.authentication.AuthenticationResponse;
import com.example.BookingManager.consoleTextMod.ColorText;
import com.example.BookingManager.user.User;
import com.example.BookingManager.user.UserRepository;
import com.example.BookingManager.userDetails.MyUserDetailsService;
import com.example.BookingManager.util.JwtUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    // change path to login to test this
    // for now ill change it to /testLogin
    @PostMapping(path = "/login")
    public String login(@RequestBody User user)
    {
        String tempEmail = user.getEmail();
        String tempPass = user.getPassword();

        if(tempEmail != null && tempPass != null){
            Optional<User> tempUser = userRepository.findByEmail(tempEmail); // finding the account with this email

            if(tempUser.isPresent()){
                User user1 = tempUser.get();
                if(user1.getEmail().compareTo(tempEmail) == 0 && user1.getPassword().compareTo(tempPass) == 0){
                    //return "SUCCESS LOGIN";
                    Gson gson = new Gson();
                    return gson.toJson(user); // turn object to json
                }
            }

        }

        //System.out.println(tempEmail + " " + tempPass);

        return "LOGIN FAIL";
    }

    @PostMapping(path = "/testLogin")
    public AuthenticationResponse testLogin(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        System.out.println(ColorText.ANSI_YELLOW
                + "TEST LOGIN, response to angular's post"
                + ColorText.ANSI_RESET);

        // if authentication fails, we will catch the exception
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword())
            );
        }catch(BadCredentialsException e){
            throw new Exception("From AuthService, Method: generateJwt, Mgs: Incorrect username or password", e);
        }

        // if authentication is successful, we need to return a jwt
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        Gson gson = new Gson();

        return ResponseEntity.ok(new AuthenticationResponse(jwt)).getBody();
    }

}
