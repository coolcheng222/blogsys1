package com.sealll.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.sealll.shiro.filter.JedisCloserFilter;
import com.sealll.application.user.interceptor.CorsFilter;
import org.apache.shiro.session.SessionListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

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

        //druid监控过滤器
        FilterRegistration.Dynamic druidFilter = servletContext.addFilter("druid", WebStatFilter.class);
        Map<String,String> initParams2 = new HashMap<>();
        initParams2.put("exclusions","*.js,*.css,/druid/*");
        druidFilter.setInitParameters(initParams2);
        druidFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

        //druid监控servlet
        ServletRegistration.Dynamic statView = servletContext.addServlet("statView", StatViewServlet.class);
        statView.addMapping("/druid/*");
        Map<String,String> initParams = new HashMap<>();
        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123456");
        initParams.put("allow","");
        statView.setInitParameters(initParams);

        //jedis关闭过滤器
        FilterRegistration.Dynamic jedis = servletContext.addFilter("jedis", JedisCloserFilter.class);
        jedis.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

        //请求伪装过滤器
        FilterRegistration.Dynamic hidden = servletContext.addFilter("hidden", HiddenHttpMethodFilter.class);
        hidden.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

//        验证码过滤器
//        FilterRegistration.Dynamic kaptFilter = servletContext.addFilter("kaptFilter", KapFilter.class);
//        kaptFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/login","/register");
        //shiro拦截系统
        FilterRegistration.Dynamic shiroFilter = servletContext.addFilter("shiroFilter", new DelegatingFilterProxy());
        shiroFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

        //跨域Filter
        FilterRegistration.Dynamic corsFilter = servletContext.addFilter("CorsFilter", CorsFilter.class);
        corsFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

//        验证码
//        ServletRegistration.Dynamic kaptchaServlet = servletContext.addServlet("kaptchaServlet", KaptchaServlet.class);
//        kaptchaServlet.addMapping("/kapt");
    }
}
