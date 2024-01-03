package com.example.oss.common.config;

import com.example.oss.common.web.Failure;
import com.example.oss.common.web.Success;
import com.example.oss.core.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.io.IOException;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.CACHE;
import static org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive.COOKIES;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Resource
    private DataSource dataSource;
    @Resource
    private UserService userService;

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> authorizationManager() {
        return null;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/*").permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/auth/login")
                        .successHandler(this::onAuthenticationSuccess)
                        .failureHandler(this::onAuthenticationFailure))
                // 1个用户只能在一个地方登录
                .sessionManagement(session -> session
                        .invalidSessionStrategy(this::onInvalidSessionDetected)
                        .sessionAuthenticationFailureHandler(this::onSessionAuthenticationFailure)
                        .maximumSessions(1)
                        .expiredSessionStrategy(this::onExpiredSessionDetected))
                .rememberMe(remember -> remember
                        .rememberMeParameter("rememberMe")
                        .tokenRepository(this.rememberToken())
                        .tokenValiditySeconds(20))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(this::onNonAuthenticated)
                        .accessDeniedHandler(this::onResourceUnauthorized))
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .addLogoutHandler(this::clearSiteDataOnLogout)
                        .logoutSuccessHandler(this::onLogoutSuccess))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors
                        .configurationSource(this.getCorsConfigurationSource()));

        return http.build();
    }

    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String responseBody = mapper.writeValueAsString(new Failure(SC_UNAUTHORIZED, "会话超时，请重新登录"));
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(responseBody);
    }

    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        SessionInformation sessionInformation = event.getSessionInformation();
        UserDetails principal = (UserDetails) sessionInformation.getPrincipal();
        String username = principal.getUsername();

        HttpServletResponse response = event.getResponse();
        String responseBody = new ObjectMapper().writeValueAsString(new Failure(SC_UNAUTHORIZED,  username + " 已在其它设备登录"));
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(responseBody);
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    public CorsConfigurationSource getCorsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.addAllowedOriginPattern("*");
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        cors.addExposedHeader("*");
        cors.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

    @Bean // 创建用户认证管理器，用于校验用户的密码
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this::loadUserByUsername);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean // 用户的密码加密算法
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository rememberToken() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    // 退出登录后清除站点数据
    public void clearSiteDataOnLogout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(CACHE, COOKIES));
    }

    // 根据用户名加载用户信息
    public UserDetails loadUserByUsername(String username) {
        return userService.loadUserByUsername(username);
    }


    // 用户认证成功
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String responseBody = mapper.writeValueAsString(Success.ok(true));
        response.getWriter().write(responseBody);
    }

    // 该账号已在其它地点登录
    public void onSessionAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String responseBody = new ObjectMapper().writeValueAsString(new Failure(SC_UNAUTHORIZED, "该账号已在其它地点登录"));
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(responseBody);
    }

    // 用户认证失败
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String responseBody = new ObjectMapper().writeValueAsString(new Failure(SC_UNAUTHORIZED, "用户名或密码错误"));
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(responseBody);
    }

    // 请求资源未授权
    public void onResourceUnauthorized(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        String responseBody = new ObjectMapper().writeValueAsString(new Failure(SC_FORBIDDEN, "资源未授权"));
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(responseBody);
    }

    // 请求资源时用户未认证
    public void onNonAuthenticated(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String responseBody = new ObjectMapper().writeValueAsString(new Failure(SC_UNAUTHORIZED, "用户未认证"));
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(responseBody);
    }

    // 退出登录成功
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String responseBody = mapper.writeValueAsString(Success.ok(true));
        response.getWriter().write(responseBody);
    }

}
