package com.example.demo.services;

import com.example.demo.models.SecurityUser;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEntityOptional = repository.findByUsername(username);
        if(userEntityOptional.isEmpty()){
            throw new UsernameNotFoundException("no such user found!");
        }
        return new SecurityUser(userEntityOptional.get());
    }
}
