package com.hijab.config;

import com.hijab.common.interceptor.LoggerInterceptor;
import com.hijab.common.interceptor.RateLimitInterceptor;
import com.hijab.common.interceptor.UserIdentifierInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

//@EnableWebMvc
@Configuration
@ComponentScan
public class MvcConfig implements WebMvcConfigurer {

    private final UserIdentifierInterceptor userIdentifierInterceptor;
    private final LoggerInterceptor loggerInterceptor;
    private final RateLimitInterceptor rateLimitInterceptor;

    public MvcConfig(UserIdentifierInterceptor userIdentifierInterceptor,
                     LoggerInterceptor loggerInterceptor,
                     RateLimitInterceptor rateLimitInterceptor) {
        this.userIdentifierInterceptor = userIdentifierInterceptor;
        this.loggerInterceptor = loggerInterceptor;
        this.rateLimitInterceptor = rateLimitInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor);
        registry.addInterceptor(rateLimitInterceptor);
//        registry.addInterceptor(userIdentifierInterceptor);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }
}
