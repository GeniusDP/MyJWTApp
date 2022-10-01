package com.example.demo.controllers;

import com.example.demo.dto.TokenResponse;
import com.example.demo.dto.UserDto;
import com.example.demo.services.LoginRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginRegisterService loginRegisterService;
    private final HttpServletResponse response;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody UserDto userDto){
        return loginRegisterService.login(userDto, response);
    }

    @PostMapping("/register")
    public String register(@RequestBody UserDto userDto){
        return loginRegisterService.register(userDto);
    }

    @PostMapping("/refreshtoken")
    public TokenResponse getNewAccessToken(@CookieValue("JWT_REFRESH_TOKEN") String refreshToken){
        return loginRegisterService.refreshToken(refreshToken);
    }

}
