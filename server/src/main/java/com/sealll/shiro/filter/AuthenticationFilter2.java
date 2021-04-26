package com.sealll.shiro.filter;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author sealll
 * @time 2021/4/16 20:12
 */
@Component
public class AuthenticationFilter2 extends FormAuthenticationFilter {
    @Override
    protected boolean isLoginRequest(ServletRequest request, ServletResponse response) {
        boolean loginRequest = super.isLoginRequest(request, response);
        HttpServletRequest req = (HttpServletRequest)request;
        boolean isPost = req.getMethod().equalsIgnoreCase("POST");
        return isPost && loginRequest;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean sup = super.isAccessAllowed(request, response, mappedValue);
        HttpServletRequest req = (HttpServletRequest)request;
        boolean b = pathsMatch(getLoginUrl(),request) && req.getMethod().equalsIgnoreCase("GET");
        return b || sup;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        return true;
    }
}
