package com.developerblog.ws.scurity;

import com.developerblog.ws.SpringApplicationContext;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    public AuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req ,
                                   HttpServletResponse res , FilterChain chain) throws IOException , ServletException {

        String header = req.getHeader(SecurityConstants.HEADER_STRING);
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            chain.doFilter(req , res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req , res);

    }
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){

        String authorizationHeader = request.getHeader(SecurityConstants.HEADER_STRING);
        if (authorizationHeader == null) {
            return null;
        }

       String token = authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX , "");

        byte[] secretKeyBytes = Base64.getEncoder().encode(SecurityConstants.getTokenSecret().getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes , SignatureAlgorithm.HS512.getJcaName());

        JwtParser jwtParser = Jwts.parser().setSigningKey(secretKey);

        Jwt<Header, Claims> jwt = jwtParser.parse(token);
        String subject = jwt.getBody().getSubject();

        if (subject == null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(subject , null , new ArrayList<>());

    }
}
