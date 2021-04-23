package com.sealll.config;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * @author sealll
 * @time 2021/3/26 19:11
 */
public class BlogWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        //字符编码过滤器
        FilterRegistration.Dynamic charencod = servletContext.addFilter("character", CharacterEncodingFilter.class);
        charencod.setInitParameter("encoding","utf-8");
        charencod.setInitParameter("forceEncoding","true");
        charencod.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

        //请求伪装过滤器
        FilterRegistration.Dynamic hidden = servletContext.addFilter("hidden", HiddenHttpMethodFilter.class);
        hidden.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");


        //shiro拦截系统
        FilterRegistration.Dynamic shiroFilter = servletContext.addFilter("shiroFilter", DelegatingFilterProxy.class);
        shiroFilter.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST),true,"/*");

//        验证码
//        ServletRegistration.Dynamic kaptchaServlet = servletContext.addServlet("kaptchaServlet", KaptchaServlet.class);
//        kaptchaServlet.addMapping("/kapt");
    }
}
