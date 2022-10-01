package com.example.demo.configs.security.auth_providers;

import com.example.demo.models.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SecurityUser securityUserWrapper = (SecurityUser)authentication.getPrincipal();
        String usernameTheirs = securityUserWrapper.getUsername();
        String passwordTheirs = securityUserWrapper.getPassword();

        SecurityUser userFromDB = (SecurityUser)userDetailsService.loadUserByUsername(usernameTheirs);
        if (passwordEncoder.matches(passwordTheirs, userFromDB.getPassword())) {
            UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                    usernameTheirs,
                    passwordTheirs,
                    userFromDB.getAuthorities()
            );
            return result;
        }
        throw new BadCredentialsException("user with such username was not found!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
