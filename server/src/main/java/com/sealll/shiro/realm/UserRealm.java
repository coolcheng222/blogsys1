package com.sealll.shiro.realm;

import com.sealll.application.role.bean.Role;
import com.sealll.application.role.service.RoleService;
import com.sealll.application.user.bean.User;
import com.sealll.application.user.service.UserService;
import com.sealll.constant.ParameterConstants;
import com.sealll.shiro.authorizer.VerySimpleAuthorizationInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author sealll
 * @time 2021/3/30 14:19
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String s = (String) principalCollection.getPrimaryPrincipal();
        Role role = roleService.getRole(s);
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            SecurityUtils.getSubject().getSession().setAttribute(ParameterConstants.ROLEATTR,role);
            AuthorizationInfo authorizationInfo = new AuthorizationInfo() {
                @Override
                public Collection<String> getRoles() {
                    return Arrays.asList(role.getRole());
                }

                @Override
                public Collection<String> getStringPermissions() {
                    return null;
                }

                @Override
                public Collection<Permission> getObjectPermissions() {
                    return null;
                }
            };
            return authorizationInfo;
        }else{
            return new AuthorizationInfo() {
                @Override
                public Collection<String> getRoles() {
                    return null;
                }

                @Override
                public Collection<String> getStringPermissions() {
                    return null;
                }

                @Override
                public Collection<Permission> getObjectPermissions() {
                    return null;
                }
            };
        }


    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        String password = new String((char[])authenticationToken.getCredentials());
        User user = new User();
        user.setUsername(username);
        User user1 = userService.getByUsername(user);
        if(username == null || user1 == null){
            throw new UnknownAccountException("incorrect password or username");
        }else if(password == null){
            throw  new IncorrectCredentialsException("incorrect password or username");
        }
        String uid = user1.getUid();
        SecurityUtils.getSubject().getSession().setAttribute(ParameterConstants.UID_SESSION_KEY,uid);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user1.getUsername(), //用户名
                user1.getPassword(), //密码
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
