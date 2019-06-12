package com.hbsd.rjxy.lunwen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("student").setViewName("student");
        registry.addViewController("index").setViewName("index");
        registry.addViewController("index.html").setViewName("index");
        registry.addViewController("teacher").setViewName("teacher");
        registry.addViewController("manner").setViewName("manner/manner");
        registry.addViewController("topiclist").setViewName("topiclist");
        registry.addViewController("topicedit").setViewName("topicedit");
        registry.addViewController("applylist").setViewName("applylist");
        registry.addViewController("question_list").setViewName("question_list");
        registry.addViewController("manner_list").setViewName("manner/manner_list");
        registry.addViewController("manner_t").setViewName("manner/manner_t");
        registry.addViewController("manner_s").setViewName("manner/manner_s");
        registry.addViewController("manner_edit").setViewName("manner/manner_edit");
        registry.addViewController("manner_s_list").setViewName("manner/manner_s_list");
        registry.addViewController("info_edit").setViewName("info_edit");
        registry.addViewController("introduce_student").setViewName("introduce_student");
        registry.addViewController("introduce_teacher").setViewName("introduce_teacher");
    }
    /*
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginHandlerInterceptor loginHandlerInterceptor=new LoginHandlerInterceptor();
        InterceptorRegistration interceptorRegistry=registry.addInterceptor(loginHandlerInterceptor);
        //设置拦截路径
        interceptorRegistry.addPathPatterns("/**");
        interceptorRegistry.excludePathPatterns("/");
        interceptorRegistry.excludePathPatterns("/login");
        interceptorRegistry.excludePathPatterns("/index");
        interceptorRegistry.excludePathPatterns("/index.html");
        interceptorRegistry.excludePathPatterns("/Login_files/**","/images/**","/scripts/**","/style/**");
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/Login_files/**")
                .addResourceLocations("classpath:/static/Login_files/");
        registry.addResourceHandler("/static/images/**")
                .addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/static/scripts/**")
                .addResourceLocations("classpath:/static/scripts/");
        registry.addResourceHandler("/static/style/**")
                .addResourceLocations("classpath:/static/style/");
    }
}
