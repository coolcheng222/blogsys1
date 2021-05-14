package com.sealll.application.role.bean;

import java.util.Date;

/**
 * @author sealll
 * @time 2021/5/14 12:51
 */
public class Role {
    private String username;
    private String role;
    private Date ttl;

    @Override
    public String toString() {
        return "Role{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", ttl=" + ttl +
                '}';
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
