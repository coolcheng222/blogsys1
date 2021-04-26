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
        String validPassword = (String)authenticationInfo.getCredentials();
        String username = (String)authenticationToken.getPrincipal();
        String password = new String((char[])authenticationToken.getCredentials());
//        System.out.println(validPassword);
        String s = passwordParser.transPassword(username, password);
//        System.out.println(s);
        return validPassword.equals(s);

    }
}
