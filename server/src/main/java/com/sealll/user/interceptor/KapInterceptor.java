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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sealll
 * @time 2021/4/20 10:27
 */
public class KapInterceptor implements HandlerInterceptor {
    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!"POST".equals(request.getMethod())){
            return true;
        }
        String parameter = request.getParameter(ParameterConstants.KAPTCHAPARAM);
        String inner = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if(parameter == null || !parameter.equals(inner)){
            response.getWriter().write(mapper.writeValueAsString(Msg.fail("keptcha error")));
            SecurityUtils.getSubject().logout();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
