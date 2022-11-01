package com.example.WithRun.service;


import com.example.WithRun.domain.User;
import com.example.WithRun.dto.SignInDTO;
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

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public User createUser(SignUpDTO signUpDTO) {
        try {
            if (isExistByUserId(signUpDTO.getUserId()) || isExistByUsername(signUpDTO.getUsername())
                    || isExistByEmail(signUpDTO.getEmail()))
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

    public Boolean validateIdAndPassword(SignInDTO signInDTO){
        User getUserByUserId = userRepository.findByUserID(signInDTO.getUserId());

        return getUserByUserId != null && passwordEncoder
                .matches(signInDTO.getPassword(), getUserByUserId.getUserPassword());
    }

    public User getUserInRepo(SignInDTO signInDTO){
        if(validateIdAndPassword(signInDTO)){
            return userRepository.findByUserID(signInDTO.getUserId());
        }
        else return null;
    }

    public Boolean isExistByUserId(String userId) {
        return userRepository.existsByUserID(userId);
    }

    public Boolean isExistByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean isExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
