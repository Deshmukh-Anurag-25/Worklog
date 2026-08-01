package com.anurag.worklog.service;

import com.anurag.worklog.dto.LoginRequest;
import com.anurag.worklog.dto.RegisterRequest;
import com.anurag.worklog.dto.UserResponse;
import com.anurag.worklog.entity.User;
import com.anurag.worklog.exception.DuplicateResourceException;
import com.anurag.worklog.exception.InvalidCredentialsException;
import com.anurag.worklog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new DuplicateResourceException("Username already exists");
        }

        if(userRepository.existsByEmail(request.getEmail())){
            throw new DuplicateResourceException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User saved = userRepository.save(user);
        return toUserResponse(saved);
    }

    public UserResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return toUserResponse(user);
    }

    private UserResponse toUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}
