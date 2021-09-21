/*
    Input structure that takes in username and password from a post request

 */

package com.example.BookingManager.authentication;

public class AuthenticationRequest {

    // this is email
    // this is what we will be sending from postman
    private String email;
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
