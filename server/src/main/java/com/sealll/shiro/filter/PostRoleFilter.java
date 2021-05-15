package com.sealll.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sealll.application.role.bean.Role;
import com.sealll.bean.Msg;
import com.sealll.constant.ParameterConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author sealll
 * @time 2021/5/15 12:00
 */
@Component
public class PostRoleFilter extends RolesAuthorizationFilter {
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        boolean b = false;
        Subject subject = this.getSubject(request, response);
        String[] rolesArray = (String[])((String[])mappedValue);
        for (String s : rolesArray) {
            if(subject.hasRole(s)){
                b = true;
                break;
            }
        }
        HttpServletRequest req = (HttpServletRequest) request;
        String method = req.getMethod();
        boolean res = b || method.equals(HttpMethod.GET.name());
        return res;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = this.getSubject(request, response);
        if (subject.getPrincipal() == null) {
            this.saveRequestAndRedirectToLogin(request, response);
        } else {
            Role role = (Role)SecurityUtils.getSubject().getSession().getAttribute(ParameterConstants.ROLEATTR);
            response.getWriter().write(objectMapper.writeValueAsString(Msg.permit("无权限添加内容").extend(role)));
        }
        return false;
    }
}
