package com.sealll.shiro.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sealll.application.user.bean.User;
import com.sealll.application.user.service.UserService;
import com.sealll.bean.Msg;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

/**
 * @author sealll
 * @time 2021/5/15 11:04
 */
@Component
public class GetSelfFilter extends AdviceFilter {

    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        if(!req.getMethod().equals(HttpMethod.GET.name())){
            return true;
        }
        String principal = (String)SecurityUtils.getSubject().getPrincipal();
        User user = new User();
        user.setUsername(principal);
        String uid = userService.getByUsername(user).getUid();
        String path = req.getServletPath();
        if(path.contains("/user/")){
            String[] split = path.split("/user/");
            String substring = null;
            if(split[1].length() >= 17){
                substring = split[1].substring(0, 17);
            }
            if(uid.equals(substring)){
                return true;
            }else{
                response.getWriter().write(
                    objectMapper.writeValueAsString(Msg.permit("没有权限查看"))
                );
                return false;
            }
        }else{
            return true;
        }
    }
}
