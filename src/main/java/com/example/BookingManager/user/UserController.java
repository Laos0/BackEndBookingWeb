/*
    This controller takes care of endpoints/api
 */

package com.example.BookingManager.user;

import com.example.BookingManager.userDetails.MyUserDetails;
import com.example.BookingManager.userDetails.MyUserDetailsService;
import com.example.BookingManager.util.JwtUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(path = "/users/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){ // getting the id in the path defining it as Long stored in id
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping(path = "/users/add")
    public ResponseEntity<User> addUser(@RequestBody User user){

        // set defaults to user's status, isActive, userRole and enable
        UserAutoConfig defaultUserSetting = new UserAutoConfig(user);

        User newUser = userService.addUser(defaultUserSetting.getUser());

        // ORIGINAL, if broken delete all on top
        //User newUser = userService.addUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User updateUser = userService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Angular side will send in a token as a string
    // we will then extract the email from that token
    // then find the details of that account and return it
    @PostMapping(path = "/user/details")
    public String getUserById(@RequestBody String token){ // getting the id in the path defining it as Long stored in id

        User user = null;
        Gson gson = new Gson();

        // check if token is expired
        if(jwtUtil.validateIncomingToken(token)){
            // then extract the subject from the jwt and find the user account
            System.out.println("THIS IS HIT MAN");
            user = userService.findByEmail(jwtUtil.extractUsername(token));
        }

        //User user = new User(10L, "Sony", "lao", "715-111-111", "iman@gmail.com", "catman", "online", true, UserRole.ADMIN, true);

        return gson.toJson(user);
    }

}
