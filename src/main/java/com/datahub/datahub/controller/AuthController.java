package com.datahub.datahub.controller;

import com.datahub.datahub.entity.User;
import com.datahub.datahub.model.ApiResponse;
import com.datahub.datahub.model.LoginDTO;
import com.datahub.datahub.model.RegisterDto;
import com.datahub.datahub.security.JwtProvider;
import com.datahub.datahub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AuthService authService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody RegisterDto registerDto){
        ApiResponse apiResponse = authService.registerUser(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse.getMessage());
    }

    @PostMapping("/login")
    public HttpEntity<?> loginUser(@RequestBody LoginDTO loginDto){
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(user.getEmail());

            return ResponseEntity.ok(token);
        }
        catch (Exception e){
            return ResponseEntity.ok(new ApiResponse("Email or password is wrong", false));
        }
    }

    @PutMapping("/verifyEmail")
    public HttpEntity<?> verifyEmail(@RequestParam String email, @RequestParam int emailCode){
        ApiResponse apiResponse = authService.verifyEmail(email, emailCode);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse.getMessage());
    }
}
