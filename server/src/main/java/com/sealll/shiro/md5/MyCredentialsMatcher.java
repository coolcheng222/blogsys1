package com.sealll.shiro.md5;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author sealll
 * @time 2021/4/16 20:06
 */
@Component
public class MyCredentialsMatcher implements CredentialsMatcher {
    @Autowired
    public PasswordParser passwordParser;
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        String password = (String)authenticationInfo.getCredentials();
        PrincipalCollection principals = authenticationInfo.getPrincipals();
        String username = (String) principals.getPrimaryPrincipal();

        String s = passwordParser.transPassword(username, password);
        String credentials1 = (String) authenticationToken.getCredentials();

        return credentials1.equals(s);

    }
}
