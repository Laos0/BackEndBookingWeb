/*
    This class/filter will intercept every requests just once and
    examine the header
 */

package com.example.BookingManager.filters;

import com.example.BookingManager.consoleTextMod.ColorText;
import com.example.BookingManager.userDetails.MyUserDetailsService;
import com.example.BookingManager.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{



        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        System.out.println(ColorText.ANSI_YELLOW +"From JwtRequestFilter.java, Method: doFilterInternal, Variable: authorizationHeader = "
                + authorizationHeader + ColorText.ANSI_RESET);
        // verfiy if header is null
        // "Bearer " means whatever is coming after that is the jwt
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            System.out.println("NO HEADER");
            //"Bearer " is equal to 7
            // so substring will remove the first 7 characters and return the rest
            jwt = authorizationHeader.substring(7);
            email = jwtUtil.extractUsername(jwt);
        }

        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            // validate the token once userDetails is obtained
            if(jwtUtil.validateToken(jwt, userDetails)){
                // creating a new userPassAuth user and password to whats in the request
                UsernamePasswordAuthenticationToken UPAT = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                UPAT
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(UPAT);
            }

        }
        filterChain.doFilter(request, response);

    }
}
