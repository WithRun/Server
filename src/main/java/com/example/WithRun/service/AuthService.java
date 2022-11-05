package com.example.WithRun.service;


import com.example.WithRun.domain.User;
import com.example.WithRun.dto.SignUpDTO;
import com.example.WithRun.repository.UserRepository;
import com.example.WithRun.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    UserService userService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public User createUser(SignUpDTO signUpDTO) {
        try {
            if (userService.isExistByUserId(signUpDTO.getUserId())
                    || userService.isExistByUsername(signUpDTO.getUsername())
                    || userService.isExistByEmail(signUpDTO.getEmail()))
                throw new Exception();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        User newUser = User.builder().userID(signUpDTO.getUserId()).email(signUpDTO.getEmail())
                .userPassword(passwordEncoder.encode(signUpDTO.getPassword())
                ).level(0).username(signUpDTO.getUsername()).role("ROLE_USER")
                .gender(signUpDTO.getGender()).build();

        userRepository.save(newUser);
        return newUser;
    }

    public String createToken(User user){
        final String token = tokenProvider.create(user);
        user.setToken(token);
        userRepository.save(user);
        return token;
    }


}
