package com.example.BookingManager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class HomeResource {

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
}
