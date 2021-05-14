package com.sealll.application.role.service.impl;

import com.github.pagehelper.PageHelper;
import com.sealll.application.role.bean.Role;
import com.sealll.application.role.service.RoleService;
import com.sealll.constant.PageConstants;
import com.sealll.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.rmic.Constants;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/14 11:52
 */
@Service
@Transactional
@Async
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public Role getRole(String username) {
        return roleMapper.getRole(username);
    }

    @Override
    public boolean updateRole(Role role) {
        return roleMapper.updateRole(role);
    }

    @Override
    public boolean deleteExpire(Long time) {
        return roleMapper.deleteExpire(time);
    }

    @Override
    public List<Role> getByRoleName(String roleName,Integer page) {
        PageHelper.startPage(page, PageConstants.PAGESIZE);
        return roleMapper.getByRoleName(roleName);
    }

    @Override
    public List<Role> getAll(Integer page) {
        PageHelper.startPage(page,PageConstants.PAGESIZE);
        List<Role> all = roleMapper.getAll();
        return all;
    }
}
