package com.sealll.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sealll.application.user.bean.User;
import com.sealll.bean.Msg;
import com.sealll.constant.ParameterConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.logging.Filter;

/**
 * @author sealll
 * @time 2021/4/25 9:53
 */
@Component
public class AnnoFilter2 extends AdviceFilter {
    @Autowired
    private ObjectMapper mapper;
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            String uid = (String)subject.getSession()
                    .getAttribute(ParameterConstants.UID_SESSION_KEY);
            String username = (String) subject.getPrincipal();
            User user = new User();
            user.setUsername(username);
            user.setUid(uid);
            response.getWriter()
                    .write(mapper.writeValueAsString(Msg.success("").extend(user)));
            return false;
        }else{
            return true;
        }
    }
}
