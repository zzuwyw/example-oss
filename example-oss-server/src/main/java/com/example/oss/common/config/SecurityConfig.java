package com.example.oss.common.config;

import com.example.oss.common.web.Failure;
import com.example.oss.common.web.Success;
import com.example.oss.core.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private UserService userService;

    @Bean // 创建用户认证管理器，用于校验用户的密码
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean // 用户的密码加密算法
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/auth/login")
                        .successHandler(this::onAuthenticationSuccess)
                        .failureHandler(this::onAuthenticationFailure))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(this::onNonAuthenticated)
                        .accessDeniedHandler(this::onResourceUnauthorized))
                .logout(logout -> logout
                        .logoutUrl("/auth/logout"))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    // 用户认证成功
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String responseBody = mapper.writeValueAsString(Success.ok(true));
        response.getWriter().write(responseBody);
    }

    // 用户认证失败
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String responseBody = mapper.writeValueAsString(new Failure(HttpServletResponse.SC_UNAUTHORIZED, "用户名或密码错误"));
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(responseBody);
    }

    // 请求资源未授权
    public void onResourceUnauthorized(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String responseBody = mapper.writeValueAsString(new Failure(HttpServletResponse.SC_FORBIDDEN, "资源未授权"));
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(responseBody);
    }

    // 请求资源时用户未认证
    public void onNonAuthenticated(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String responseBody = mapper.writeValueAsString(new Failure(HttpServletResponse.SC_UNAUTHORIZED, "用户未认证"));
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(responseBody);
    }



}
