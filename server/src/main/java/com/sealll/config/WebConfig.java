package com.sealll.config;

import com.sealll.application.history.aop.PostAspect;
import com.sealll.application.history.aop.SearchAspect;
import com.sealll.application.messaging.aop.PickTimeToMessage;
import com.sealll.application.topic.aop.SubscribeAspect;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * @author sealll
 * @time 2021/3/26 19:55
 */
@Configuration
@EnableWebMvc
@ComponentScan(value = "com.sealll", useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)
})
@EnableAspectJAutoProxy
public class WebConfig extends WebMvcConfigurerAdapter {
    /*@Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        super.configureViewResolvers(registry);
        InternalResourceViewResolver i = new InternalResourceViewResolver();
        i.setPrefix("/WEB-INF/");
        i.setSuffix(".html");
        registry.viewResolver(i);
    }*/

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public SearchAspect searchAspect(){
        return new SearchAspect();
    }
    @Bean
    public PostAspect postAspect(){
        return new PostAspect();
    }

    @Bean
    public PickTimeToMessage pickTimeToMessage(){
        return new PickTimeToMessage();
    }

    @Bean
    public SubscribeAspect subscribeAspect(){
        return new SubscribeAspect();
    }
}
