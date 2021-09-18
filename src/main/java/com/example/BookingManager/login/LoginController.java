package com.example.BookingManager.login;

import com.example.BookingManager.user.User;
import com.example.BookingManager.user.UserRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(path = "/login")
    public String login(@RequestBody User user){
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

}
