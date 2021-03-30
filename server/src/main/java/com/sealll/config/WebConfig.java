package com.sealll.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.util.EnumSet;

/**
 * @author sealll
 * @time 2021/3/26 19:55
 */
@Configuration
@EnableWebMvc
@ComponentScan(value = "com.sealll",useDefaultFilters = false,includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = Controller.class)
})
public class WebConfig {
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver i = new InternalResourceViewResolver();
        i.setPrefix("/WEB-INF/");
        i.setSuffix(".html");
        return i;
    }


    @Configuration
    static class WebServletInitializer extends WebMvcConfigurerAdapter{
        @Override
        public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
            configurer.enable();
        }
    }
    @Configuration
    static class ServletConfig implements ServletContextAware{
        @Override
        public void setServletContext(ServletContext servletContext) {
            //字符编码过滤器
            FilterRegistration.Dynamic charencod = servletContext.addFilter("character", CharacterEncodingFilter.class);
            charencod.setInitParameter("encoding","utf-8");
            charencod.setInitParameter("forceEncoding","true");
            charencod.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

            FilterRegistration.Dynamic hidden = servletContext.addFilter("hidden", HiddenHttpMethodFilter.class);
            hidden.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");


            FilterRegistration.Dynamic shiroFilter = servletContext.addFilter("shiroFilter", DelegatingFilterProxy.class);
            shiroFilter.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST),true,"/*");
        }

    }
}
