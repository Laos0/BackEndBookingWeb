package com.example.BookingManager.userDetails;

import com.example.BookingManager.consoleTextMod.ColorText;
import com.example.BookingManager.user.User;
import com.example.BookingManager.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println(ColorText.ANSI_YELLOW + "From: MyUserDetailsService, Method loadUserByUsername, variable: email = " + email + ColorText.ANSI_RESET);
        Optional<User> user = userRepository.findByEmail(email);

        user.orElseThrow(() -> new UsernameNotFoundException(ColorText.ANSI_RED + "Email notssss found: " + email + ColorText.ANSI_RESET));
        return user.map(MyUserDetails::new).get();
    }
}
