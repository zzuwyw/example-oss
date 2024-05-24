package com.example.oss.common.config;

import com.example.oss.common.web.Failure;
import com.example.oss.common.web.Result;
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

    /**
     * 会话超时
     */
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Failure failure = Failure.failure("会话超时，请重新登录");
        responseFailure(response, HttpStatus.UNAUTHORIZED, failure);
    }

    /**
     * 用户已在其它设备登录
     */
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        SessionInformation sessionInformation = event.getSessionInformation();
        UserDetails principal = (UserDetails) sessionInformation.getPrincipal();
        HttpServletResponse response = event.getResponse();
        Failure failure = Failure.failure(principal.getUsername() + " 已在其它设备登录");
        responseFailure(response, HttpStatus.UNAUTHORIZED, failure);
    }

    /**
     * 用户认证成功
     */
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        responseSuccess(response);
    }

    /**
     * 该账号已在其它地点登录
     */
    public void onSessionAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        Failure failure = Failure.failure("该账号已在其它地点登录");
        responseFailure(response, HttpStatus.UNAUTHORIZED, failure);
    }

    /**
     * 用户认证失败-用户名或密码错误
     */
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        Failure failure = Failure.failure("用户名或密码错误");
        responseFailure(response, HttpStatus.UNAUTHORIZED, failure);
    }

    /**
     * 请求资源未授权
     */
    public void onResourceUnauthorized(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        Failure failure = Failure.failure("资源未授权");
        responseFailure(response, HttpStatus.FORBIDDEN, failure);
    }

    /**
     * 请求资源时用户未认证
     */
    public void onNonAuthenticated(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Failure failure = Failure.failure("用户未认证");
        responseFailure(response, HttpStatus.UNAUTHORIZED, failure);
    }

    /**
     * 退出登录成功
     */
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        responseSuccess(response);
    }

    /**
     * 响应成功
     *
     * @param response 待响应的响应体
     * @throws IOException 响应异常
     */
    private void responseSuccess(HttpServletResponse response) throws IOException {
        Success success = Success.ok(true);
        response(response, HttpStatus.OK, success);
    }

    /**
     * 响应失败
     *
     * @param response 响应体
     * @param httpStatus 响应状态
     * @param failure 错误信息
     * @throws IOException 响应异常
     */
    private void responseFailure(HttpServletResponse response, HttpStatus httpStatus, Failure failure) throws IOException {
        response(response, httpStatus, failure);
    }

    /**
     * 向响应体写响应内容
     *
     * @param response 待响应的响应体
     * @param httpStatus 待响应的响应状态
     * @param result 待响应的响应结果
     * @throws IOException 响应异常
     */
    private void response(HttpServletResponse response, HttpStatus httpStatus, Result result) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(result);
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(httpStatus.value());
        response.getWriter().write(body);
    }


}
