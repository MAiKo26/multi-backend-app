package tn.maiko26.springboot.interceptor;

import com.google.common.util.concurrent.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import tn.maiko26.springboot.exception.CustomException;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);
    private final RateLimiter rateLimiter;

    public RateLimitInterceptor() {
        // Allow 100 requests per minute
        this.rateLimiter = RateLimiter.create(100.0/60.0);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!rateLimiter.tryAcquire()) {
            logger.warn("Rate limit exceeded for IP: {}", request.getRemoteAddr());
            throw new CustomException("Too many requests, please try again later", 429);
        }
        return true;
    }
}