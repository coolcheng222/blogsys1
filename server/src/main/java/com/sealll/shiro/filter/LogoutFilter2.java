package com.sealll.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sealll.bean.Msg;
import com.sealll.constant.ParameterConstants;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Parameter;

/**
 * @author sealll
 * @time 2021/4/27 16:13
 */
@Component
public class LogoutFilter2 extends LogoutFilter {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private Logger log;

    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = this.getSubject(request, response);
        subject.getSession().removeAttribute(ParameterConstants.UID_SESSION_KEY);
        String redirectUrl = this.getRedirectUrl(request, response, subject);

        try {
            subject.logout();
        } catch (SessionException var6) {
            log.debug("Encountered session exception during logout.  This can generally safely be ignored.", var6);
        }

        this.issueRedirect(request, response, redirectUrl);
        return false;
    }
    @Override
    protected void issueRedirect(ServletRequest request, ServletResponse response, String redirectUrl) throws Exception {
        response.getWriter().write(
                mapper.writeValueAsString(Msg.success("log out success"))
        );
    }
}
