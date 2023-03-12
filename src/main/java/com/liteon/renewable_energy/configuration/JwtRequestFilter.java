package com.liteon.renewable_energy.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.liteon.renewable_energy.controller.ExceptionRestResponse;
import com.liteon.renewable_energy.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    private final ObjectMapper objectMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private MyUserDetailsService userDetailsService;

    public JwtRequestFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private void handleInvalidCorrelationId(HttpServletResponse response, String message) throws IOException {
        ExceptionRestResponse errorResponse = new ExceptionRestResponse(401, message);

        response.setContentType("application/json");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, objectMapper.writeValueAsString(errorResponse));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, HttpClientErrorException{
        final String requestTokenHeader = request.getHeader("Authorization");
        String email = null;
        String jwtToken = null;

        if (request.getRequestURL().toString().contains("/api/auth-")) {
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);
                try {
                    email = jwtTokenUtil.getUsernameFromToken(jwtToken);
                } catch (IllegalArgumentException e) {
                    //handleInvalidCorrelationId(response, "Invalid JWT Token");
                    response.sendError(401, "Invalid JWT Token");
                    //throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Invalid JWT Token");
                } catch (ExpiredJwtException e) {
                    //handleInvalidCorrelationId(response, "Invalid JWT Token has expired");
                    response.sendError(401, "Invalid JWT Token has expired");
                    //throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Invalid JWT Token has expired");
                }
            } else {
                //handleInvalidCorrelationId(response, "Invalid JWT Token does not begin with Bearer String");
                response.sendError(401, "Invalid JWT Token does not begin with Bearer String");
                //throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Invalid JWT Token does not begin with Bearer String");
            }

//            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
//                if (jwtTokenUtil.validateToken(jwtToken, email)) {
//                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                            userDetails,
//                            null,
//                            userDetails.getAuthorities()
//                    );
//                    authToken.setDetails(
//                            new WebAuthenticationDetailsSource().buildDetails(request)
//                    );
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            }
        }
        chain.doFilter(request, response);
    }
}
