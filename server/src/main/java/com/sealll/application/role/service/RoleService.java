package com.sealll.application.role.service;

import com.sealll.application.role.bean.Role;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/14 11:52
 */
public interface RoleService {
    public Role getRole(String username);
    public boolean updateRole(Role role);
    public boolean deleteExpire(Long time);
    public List<Role> getByRoleName(String roleName,Integer page);
    public List<Role> getByRoleId(String roleId,Integer page);
    public List<Role> getAll(Integer page);
    public List<Role> getAllRole();
}
