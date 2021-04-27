package com.sealll.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sealll.bean.Msg;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author sealll
 * @time 2021/4/27 16:13
 */
@Component
public class LogoutFilter2 extends LogoutFilter {
    @Autowired
    private ObjectMapper mapper;
    @Override
    protected void issueRedirect(ServletRequest request, ServletResponse response, String redirectUrl) throws Exception {
        response.getWriter().write(
                mapper.writeValueAsString(Msg.success("log out success"))
        );
    }
}
