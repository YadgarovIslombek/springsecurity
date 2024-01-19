package uz.islombek.course.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.islombek.course.springsecurity.service.PersonDetailService;

import java.util.Collections;


public class AuthProviderImpl implements AuthenticationProvider {
    private final PersonDetailService personDetailService;

    @Autowired
    public AuthProviderImpl(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();//login

        String parol = authentication.getCredentials().toString();//parrol
        UserDetails userDetails = personDetailService.loadUserByUsername(name);//dbda bomi yoqmi
        if (!parol.equals(userDetails.getPassword())){
            throw new BadCredentialsException("Incorrect password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,parol, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
