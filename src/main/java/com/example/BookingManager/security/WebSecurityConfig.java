package com.example.BookingManager.security;

import net.bytebuddy.build.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    // Auth for users
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService);



// TEST CODE
//        auth
//                .inMemoryAuthentication()
//                .withUser("blah")
//                .password(passwordEncoder().encode("blah"))
//                .roles("USER")
//                .and()
//                .withUser("foo")
//                .password(passwordEncoder().encode("foo"))
//                .roles("ADMIN");

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*
            NOTE: Spring Security hasRole() not working

            If you use hasRole('ADMIN'), in your ADMIN Enum must be ROLE_ADMIN instead of ADMIN.
            If you use hasAuthority('ADMIN'), your ADMIN Enum must be ADMIN.
            In spring security, hasRole() is the same as hasAuthority(), but hasRole()
            function map with Authority without ROLE_ prefix.
         */
        http
                .csrf().disable() // this is temp so we can post request
                .authorizeRequests()
                .antMatchers("/admin").hasAuthority("ADMIN")
                .antMatchers("/user").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/").permitAll()
                .and().formLogin();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        //return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

}
