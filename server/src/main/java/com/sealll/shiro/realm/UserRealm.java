package com.sealll.shiro.realm;

import com.sealll.user.bean.User;
import com.sealll.user.service.UserService;
import com.sealll.shiro.authorizer.VerySimpleAuthorizationInfo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sealll
 * @time 2021/3/30 14:19
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String s = (String) principalCollection.getPrimaryPrincipal();
        AuthorizationInfo authorizationInfo = new VerySimpleAuthorizationInfo(s);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        String password = (String)authenticationToken.getCredentials();
        User user = new User();
        user.setUsername(username);
        User user1 = userService.getByUsername(user);
        if(username == null || user1 == null){
            throw new UnknownAccountException("incorrect password or username");
        }else if(password == null || !password.equals(user1.getPassword())){
            throw  new IncorrectCredentialsException("incorrect password or username");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user1.getUsername(), //用户名
                user1.getPassword(), //密码
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
