package com.example.WithRun.service;

import com.example.WithRun.config.UserInfo.GoogleUserInfo;
import com.example.WithRun.config.UserInfo.NaverUserInfo;
import com.example.WithRun.config.UserInfo.OAuth2UserInfo;
import com.example.WithRun.config.auth.PrincipalDetails;
import com.example.WithRun.domain.User;
import com.example.WithRun.repository.UserRepository;
import com.example.WithRun.security.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenProvider tokenProvider;

    @Override
//    @SuppressWarnings("unchecked")
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        System.out.println("getAttributes : " + userRequest.getClientRegistration());
        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo=null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("google login request");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("naver login request");
            ObjectMapper objectMapper = new ObjectMapper();
            //noinspection unchecked
            oAuth2UserInfo = new NaverUserInfo(objectMapper.convertValue(oAuth2User.getAttributes().get("response"),Map.class));
        }else System.out.println("Invalid login request");

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId;
        String password ="password";
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";


        User user = userRepository.findByUsername(username);
        if(user ==null){
            user = User.builder().username(username).userPassword(password)
                    .email(email).provider(provider).role(role)
                    .providerId(providerId).build();
            userRepository.save(user);
        }
        String token = tokenProvider.create(user);
        user.setToken(token);
        userRepository.save(user);

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
