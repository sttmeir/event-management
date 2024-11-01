package com.mfortune.event.management.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private final HttpServletRequest request;

    public LoggingAspect(HttpServletRequest request) {
        this.request = request;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {

    }

    @Before("controllerPointcut()")
    public void logRequest(JoinPoint joinPoint) {
        String ipAddress = request.getRemoteAddr();
        String url = request.getRequestURL().toString();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String args = Arrays.toString(joinPoint.getArgs());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "Anonymous";

        log.info("Request - IP: {}, URL: {}, Username: {}, Class: {}, Method: {}, Args: {}",
                ipAddress, url, username, className, methodName, args);
    }

    @AfterReturning(value = "controllerPointcut()", returning = "response")
    public void logResponse(JoinPoint joinPoint, Object response) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "Anonymous";

        if (response instanceof ResponseEntity) {
            HttpStatus status = HttpStatus.valueOf(((ResponseEntity<?>) response).getStatusCode().value());
            log.info("Response - Status: {}, Username: {}, Class: {}, Method: {}, Response Body: {}",
                    status, username, className, methodName, ((ResponseEntity<?>) response).getBody());
        } else {
            log.info("Response - Username: {}, Class: {}, Method: {}, Response Body: {}",
                    username, className, methodName, response);
        }
    }
}
