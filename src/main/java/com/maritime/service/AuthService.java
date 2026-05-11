package com.maritime.service;

import com.maritime.dto.AuthResponse;
import com.maritime.dto.LoginRequest;
import com.maritime.dto.RegisterRequest;
import com.maritime.enums.Role;
import com.maritime.model.Ship;
import com.maritime.model.User;
import com.maritime.repository.ShipRepository;
import com.maritime.repository.UserRepository;
import com.maritime.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final ShipRepository shipRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        
        Ship ship = null;
        if (request.getShipId() != null) {
            ship = shipRepository.findById(request.getShipId())
                    .orElseThrow(() -> new RuntimeException("Ship not found"));
        }
        
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .ship(ship)
                .build();
        
        userRepository.save(user);
        
        String token = jwtUtil.generateToken(user);
        
        return new AuthResponse(token, user.getRole().name(), user.getName());
    }
    
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        String token = jwtUtil.generateToken(user);
        
        return new AuthResponse(token, user.getRole().name(), user.getName());
    }
}
