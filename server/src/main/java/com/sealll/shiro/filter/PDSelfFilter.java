package com.sealll.shiro.filter;

import com.sealll.application.user.bean.User;
import com.sealll.application.user.service.UserService;
import com.sealll.constant.ParameterConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author sealll
 * @time 2021/5/15 12:10
 */
@Component
public class PDSelfFilter extends AdviceFilter {
    @Autowired
    private UserService userService;
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        if(!req.getMethod().equals(HttpMethod.DELETE.name()) && !req.getMethod().equals(HttpMethod.PUT.name())){
            return true;
        }
        String principal = (String) SecurityUtils.getSubject().getPrincipal();
        User user = new User();
        user.setUsername(principal);
        System.out.println(SecurityUtils.getSubject().getSession().getAttribute(ParameterConstants.UID_SESSION_KEY));
        String uid = userService.getByUsername(user).getUid();
        String uid1 = req.getParameter("uid");
        if(uid1.equals(uid)){
            return true;
        }else{
            return false;
        }
    }
}
