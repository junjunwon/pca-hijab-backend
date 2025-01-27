package com.hijab.config;

import com.hijab.common.interceptor.LoggerInterceptor;
import com.hijab.common.interceptor.RateLimitInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableWebMvc
@Configuration
@ComponentScan
public class MvcConfig implements WebMvcConfigurer {

    private final LoggerInterceptor loggerInterceptor;
    private final RateLimitInterceptor rateLimitInterceptor;

    public MvcConfig(LoggerInterceptor loggerInterceptor, RateLimitInterceptor rateLimitInterceptor) {
        this.loggerInterceptor = loggerInterceptor;
        this.rateLimitInterceptor = rateLimitInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor);
        registry.addInterceptor(rateLimitInterceptor);
    }
}
