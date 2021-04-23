package com.sealll.user.interceptor;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Constants;
import com.sealll.bean.Msg;
import com.sealll.constant.ParameterConstants;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sealll
 * @time 2021/4/20 10:27
 */
public class KapFilter implements Filter {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(!"POST".equals(((HttpServletRequest)request).getMethod())){
//            return true;
            chain.doFilter(request,response);
        }
        String parameter = request.getParameter(ParameterConstants.KAPTCHAPARAM);
        String inner = (String)((HttpServletRequest)request).getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if(parameter == null || !parameter.equals(inner)){
            response.getWriter().write(mapper.writeValueAsString(Msg.fail("keptcha error")));
            SecurityUtils.getSubject().logout();
            return;
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
