package com.datahub.datahub.service;

import com.datahub.datahub.entity.User;
import com.datahub.datahub.entity.enums.SystemRoleName;
import com.datahub.datahub.model.ApiResponse;
import com.datahub.datahub.model.RegisterDto;
import com.datahub.datahub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JavaMailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public ApiResponse registerUser(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail()))
            return new ApiResponse("You have already registered", false);

        User user = new User(
                registerDto.getFirstname(),
                registerDto.getEmail(),
                encoder.encode(registerDto.getPassword()),
                SystemRoleName.SYSTEM_ROLE_USER
        );
        int securityCode = new Random().nextInt(9999);
        user.setSecurityCode(securityCode);
        user.setInitialLetter(registerDto.getFirstname().substring(0,1));
        Boolean sendMail = sendMail(user.getEmail(), user.getSecurityCode());
        userRepository.save(user);
        return new ApiResponse("User is registered",true);
    }

    public Boolean sendMail(String sendingEmail,int emailCode){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("Verification Code");
            mailMessage.setFrom("DataHub.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setText(String.valueOf(emailCode));
            mailSender.send(mailMessage);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public ApiResponse verifyEmail(String email, int emailCode) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();

            if (emailCode == user.getSecurityCode()){
                user.setEnabled(true);
                user.setSecurityCode(0);
                userRepository.save(user);
                return new ApiResponse("Code is correct",true);
            }
            return new ApiResponse("Code is wrong",false);
        }
        return new ApiResponse("Email is wrong", false);
    }
}
