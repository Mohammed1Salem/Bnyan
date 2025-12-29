package com.example.bnyan.Service;

import com.example.bnyan.Api.ApiException;
import com.example.bnyan.Model.User;
import com.example.bnyan.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findUserByUsername(username);
        if (user == null) throw new ApiException("Wrong username or password!");
        return user;
    }
}
