package com.example.BookingManager.security;


import com.example.BookingManager.filters.JwtRequestFilter;
import net.bytebuddy.build.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

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

        http.csrf().disable()
                .authorizeRequests().antMatchers("/authenticate").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


        // UNCOMMENT THIS IS IT FAILS
        /*
            NOTE: Spring Security hasRole() not working

            If you use hasRole('ADMIN'), in your ADMIN Enum must be "ROLE_ADMIN" instead of "ADMIN".
            If you use hasAuthority('ADMIN'), your ADMIN Enum must be ADMIN.
            In spring security, hasRole() is the same as hasAuthority(), but hasRole()
            function map with Authority without ROLE_ prefix.

        http
                .csrf().disable() // this is temp so we can post request
                .authorizeRequests()
                .antMatchers("/admin").hasAuthority("ADMIN")
                //.antMatchers("/api/v1/admin/users/all").hasAuthority("ADMIN")
                .antMatchers("/user").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/").permitAll()
                .and().formLogin();

         */
    }

    // Consider defining a bean of type
    // 'org.springframework.security.authentication.AuthenticationManager' in your configuration.
    // this override will fix this problem
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        //return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

}
