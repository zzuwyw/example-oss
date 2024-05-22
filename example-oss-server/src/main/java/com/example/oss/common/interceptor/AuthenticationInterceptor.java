package com.example.oss.common.interceptor;

import com.example.oss.core.user.domain.response.Principal;
import com.example.oss.core.user.service.AuthenticationService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Resource
    private AuthenticationService authenticationService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        // 没有认证的用户框架会自动分配匿名用户，匿名用户也是认证状态
        if (authentication.isAuthenticated()) {
            if (!(authentication.getPrincipal() instanceof UserDetails userDetails)) {
                return false;
            } else {
                Principal principal = getPrincipal(userDetails.getUsername());
                request.getSession().setAttribute("principal", principal);
            }
        }
        return true;
    }

    // 获取当事人
    Principal getPrincipal(String username) {
        return authenticationService.getPrincipal(username);
    }

}
