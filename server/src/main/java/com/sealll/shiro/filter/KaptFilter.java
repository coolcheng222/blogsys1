package com.sealll.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.Constants;
import com.sealll.bean.Msg;
import com.sealll.constant.ParameterConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author sealll
 * @time 2021/4/25 9:26
 */
@Component
public class KaptFilter extends AdviceFilter {
    @Autowired
    private ObjectMapper mapper;
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        if(!"POST".equals(((HttpServletRequest)request).getMethod())){
            return true;
        }
        String parameter = request.getParameter(ParameterConstants.KAPTCHAPARAM);
        String inner = (String)((HttpServletRequest)request).getSession().getAttribute(Constants.KAPTCHA_SESSION_CONFIG_KEY);
//        System.out.println(inner);
//        System.out.println(parameter);
        if(parameter == null || !parameter.equals(inner)){
            response.getWriter().write(mapper.writeValueAsString(Msg.fail("keptcha error")));
            SecurityUtils.getSubject().logout();
            return false;
        }
        return true;
    }
}
