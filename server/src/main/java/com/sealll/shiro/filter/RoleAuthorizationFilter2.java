package com.sealll.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sealll.application.role.bean.Role;
import com.sealll.bean.Msg;
import com.sealll.constant.ParameterConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.aop.RoleAnnotationHandler;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author sealll
 * @time 2021/5/15 14:32
 */
@Component
public class RoleAuthorizationFilter2 extends RolesAuthorizationFilter {
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

        if (b) {
            return b;
        } else {
              return b;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = this.getSubject(request, response);
        if (subject.getPrincipal() == null) {
            this.saveRequestAndRedirectToLogin(request, response);
        } else {
            Role role = (Role) SecurityUtils.getSubject().getSession().getAttribute(ParameterConstants.ROLEATTR);
            response.getWriter().write(objectMapper.writeValueAsString(Msg.permit("没有权限").extend(role)));
        }
        return false;
    }
}