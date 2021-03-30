package com.sealll.service;

import com.sealll.bean.User;
import org.springframework.stereotype.Service;

/**
 * @author sealll
 * @time 2021/3/28 16:13
 */
public interface UserService {
    //添加用户
    public void addUser(User user);

    //修改用户
    public void updateUser(User user);

    //查询用户
    public User getByUsername(User user);

    //
}
