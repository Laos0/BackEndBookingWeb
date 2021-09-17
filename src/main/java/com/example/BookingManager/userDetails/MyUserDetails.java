package com.example.BookingManager.userDetails;

import com.example.BookingManager.user.User;
import com.example.BookingManager.user.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private String status; // see if user is online or offline, change later
    private Boolean isActive = false; // for account to be active, user needs to confirm token
    private UserRole userRole;
    private Boolean enable;

    public MyUserDetails(){

    }

    public MyUserDetails(User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.isActive = user.getActive();
        this.userRole = user.getUserRole();
        this.enable = user.getEnable();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());

        System.out.println("THIS IS THE USER ROLE:" + userRole.name());

        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
