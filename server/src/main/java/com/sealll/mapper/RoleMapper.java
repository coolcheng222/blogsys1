package com.sealll.mapper;

import com.sealll.application.role.bean.Role;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/14 12:50
 */
public interface RoleMapper {
    public Role getRole(String username);
    public boolean updateRole(Role role);
    public boolean deleteExpire(Long time);
    public List<Role> getByRoleName(String roleName);
    public List<Role> getByRoleId(String roleId);
    public List<Role> getAll();
    public List<Role> getAllRole();
}
