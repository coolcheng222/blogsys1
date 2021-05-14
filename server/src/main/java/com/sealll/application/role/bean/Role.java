package com.sealll.application.role.bean;

import java.util.Date;

/**
 * @author sealll
 * @time 2021/5/14 12:51
 */
public class Role {
    private String username;
    private String role;
    private String roleId;
    private Date ttl;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Date getTtl() {
        return ttl;
    }

    public void setTtl(Date ttl) {
        this.ttl = ttl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
