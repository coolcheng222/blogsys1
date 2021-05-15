package com.sealll.shiro.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author sealll
 * @time 2021/5/15 13:33
 */
@Component
public class GetAuthenticationFilter2 extends AuthenticationFilter2 {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean b = super.isAccessAllowed(request, response, mappedValue);
        boolean get = ((HttpServletRequest) request).getMethod().equals(HttpMethod.GET.name());
        return get || b;
    }
}
