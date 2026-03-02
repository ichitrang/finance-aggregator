package com.acme.finance.auth;

import com.acme.finance.auth.dto.*;
import com.acme.finance.config.JwtService;
import com.acme.finance.domain.user.User;
import com.acme.finance.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepository userRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  public AuthResponse register(RegisterRequest req) {
    if (userRepo.existsByEmail(req.email())) throw new IllegalArgumentException("Email already exists");

    User u = new User();
    u.setEmail(req.email());
    u.setFullName(req.fullName());
    u.setPasswordHash(passwordEncoder.encode(req.password()));

    userRepo.save(u);
    return new AuthResponse(jwtService.generateToken(u.getEmail()));
  }

  public AuthResponse login(LoginRequest req) {
    User u = userRepo.findByEmail(req.email()).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    if (!passwordEncoder.matches(req.password(), u.getPasswordHash())) {
      throw new IllegalArgumentException("Invalid credentials");
    }
    return new AuthResponse(jwtService.generateToken(u.getEmail()));
  }
}