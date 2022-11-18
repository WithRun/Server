package com.example.WithRun.config;

import com.example.WithRun.security.JwtAuthenticationFilter;
import com.example.WithRun.service.PrincipalOauth2UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
@Slf4j
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    PrincipalOauth2UserService principalOauth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.cors().and().csrf().disable();
                http.httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/","/auth/**", "/email/**").permitAll()
//                .antMatchers("/user").hasRole("ROLE_USER")
//                .anyRequest().authenticated()
                .anyRequest().permitAll()
                .and()
                .oauth2Login().defaultSuccessUrl("/").userInfoEndpoint()
                .userService(principalOauth2UserService);
        // 로그인 하면 토큰이 아닌 액세스토큰+사용자 프로필정보 받음
        // 그 정보는 PrincipalOauth2userservice에가서 파라미터인
        //userrequest로 들어감


        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
        return http.build();
    }
}
