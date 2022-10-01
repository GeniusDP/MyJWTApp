package com.example.demo.services;

import com.example.demo.dto.TokenResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.BadAuthenticationException;
import com.example.demo.exceptions.RefreshTokenExpiredException;
import com.example.demo.exceptions.RegistrationException;
import com.example.demo.models.SecurityUser;
import com.example.demo.models.entities.Role;
import com.example.demo.models.entities.RoleValue;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoginRegisterService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsManager;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public TokenResponse login(UserDto userDto, HttpServletResponse response) {
        String username = userDto.username();
        String password = userDto.password();

        try {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    new SecurityUser(new User(username, password, Set.of())),
                    null
            );
            authenticationManager.authenticate(auth);
        } catch (BadCredentialsException ex) {
            throw new BadAuthenticationException("wrong login or password(or both of them)");
        }

        setRefreshTokenCookie(username, response);

        String jwtAccessToken = jwtTokenUtil.generateAccessJwtToken(username);
        return new TokenResponse(jwtAccessToken);
    }

    private void setRefreshTokenCookie(String username, HttpServletResponse response) {
        String jwtRefreshToken = jwtTokenUtil.generateRefreshJwtToken(username);

        Cookie cookie = new Cookie("JWT_REFRESH_TOKEN", jwtRefreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        User user = userRepository.findByUsername(username).get();
        user.setRefreshToken(jwtRefreshToken);
        userRepository.save(user);
    }

    public String register(UserDto userDto) {
        String username = userDto.username();
        String password = userDto.password();
        boolean userExists = userRepository.existsByUsername(username);
        if (userExists) {
            throw new RegistrationException("User with such username already exists");
        }
        Role userRole = roleRepository.findByName(RoleValue.ROLE_USER);
        User user = new User(username, password, Set.of(userRole));
        userRepository.save(user);
        return "registered successfully";
    }

    public TokenResponse refreshToken(String refreshToken) {
        if (jwtTokenUtil.tokenIsValid(refreshToken)) {
            String username = jwtTokenUtil.getUserNameFromJwtToken(refreshToken);
            String accessJwtToken = jwtTokenUtil.generateAccessJwtToken(username);
            return new TokenResponse(accessJwtToken);
        }
        throw new RefreshTokenExpiredException("refresh token has been expired, login pls");
    }

}
