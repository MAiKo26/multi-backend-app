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
public class AuthRateLimitInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthRateLimitInterceptor.class);
    private final RateLimiter rateLimiter;

    public AuthRateLimitInterceptor() {
        // 5 Auth Request per 5 minutes
        this.rateLimiter = RateLimiter.create(5.0/ (60.0 * 5));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!rateLimiter.tryAcquire()) {
            logger.warn("Too many login attempts from IP: {}", request.getRemoteAddr());
            throw new CustomException("Too many login attempts, please try again later", 429);
        }
        return true;
    }
}
