package com.sealll.shiro.authorizer;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author sealll
 * @time 2021/3/30 16:19
 */
public class VerySimpleAuthorizationInfo implements AuthorizationInfo {
    private String username;
    public VerySimpleAuthorizationInfo(String u){
        this.username = u;;
    }
    @Override
    public Collection<String> getRoles() {
        return Arrays.asList(username);
    }

    @Override
    public Collection<String> getStringPermissions() {
        return Arrays.asList(username + ":*");
    }

    @Override
    public Collection<Permission> getObjectPermissions() {
        return null;
    }
}
