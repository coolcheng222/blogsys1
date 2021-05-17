package com.sealll.application.role.controller;

import com.github.pagehelper.PageInfo;
import com.sealll.application.role.bean.Role;
import com.sealll.application.role.service.RoleService;
import com.sealll.bean.Msg;
import com.sealll.utils.ResultHandler;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Objects;

/**
 * @author sealll
 * @time 2021/5/14 11:22
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private RoleService roleService;

//    public Role getRole(String username);
    @GetMapping("/user/{username}")
    public Msg getRole(@PathVariable("username") String username){
        Role role = roleService.getRole(username);
        Msg msg = ResultHandler.handle(role, Objects::isNull, "找不到用户", role);
        return msg;
    }
//    public boolean updateRole(Role role);
    @PostMapping
    public Msg addRole(@RequestBody Role role){
        boolean b = roleService.updateRole(role);
        if(b){
            return Msg.success("修改成功");
        }else{
            return Msg.fail("修改失败");
        }
    }
//    public List<Role> getByRoleName(String roleName);
    @GetMapping("/rolename/{rolename}")
    public Msg getByRoleName(@PathVariable("rolename") String rolename,
                             @RequestParam(value="page",defaultValue = "1",required = false) Integer page){
        List<Role> byRoleName = roleService.getByRoleName(rolename,page);
        Msg msg = ResultHandler.handle(byRoleName, Objects::isNull, "找不到角色", new PageInfo<>(byRoleName));
        return msg;
    }
    @GetMapping("/id/{roleid}")
    public Msg getByRoleId(@PathVariable("roleid") String roleid,
                             @RequestParam(value="page",defaultValue = "1",required = false) Integer page){
        List<Role> byRoleName = roleService.getByRoleId(roleid,page);
        Msg msg = ResultHandler.handle(byRoleName, Objects::isNull, "找不到角色", new PageInfo<>(byRoleName));
        return msg;
    }
    @GetMapping
    public Msg getAll(@RequestParam(value="page",defaultValue = "1",required = false) Integer page){
        List<Role> all = roleService.getAll(page);
        Msg msg = ResultHandler.handle(all, Objects::isNull, "???", new PageInfo<Role>(all));
        return msg;
    }

    @GetMapping("/roles")
    public Msg getAllRole(){
        List<Role> allRole = roleService.getAllRole();
        Msg msg = ResultHandler.handle(allRole, Objects::isNull, "???", allRole);
        return msg;
    }

}
