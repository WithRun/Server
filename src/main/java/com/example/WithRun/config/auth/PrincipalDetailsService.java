package com.example.WithRun.config.auth;

import com.example.WithRun.user.domain.User;
import com.example.WithRun.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {

        User user = userRepository.findByUserID(userID);

        if (user != null) {
            return new PrincipalDetails(user);
        }
        return null;
    }
}
