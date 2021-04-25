package com.sealll.user.service.impl;

import com.sealll.shiro.md5.PasswordParser;
import com.sealll.user.bean.User;
import com.sealll.user.bean.UserExample;
import com.sealll.mapper.UserMapper;
import com.sealll.user.service.UserService;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author sealll
 * @time 2021/3/30 14:25
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;
    @Autowired
    private PasswordParser passwordParser;
    @Override
    public void addUser(User user) {
        Assert.notNull(user.getUsername());
        Assert.notNull(user.getPassword());
        String s = passwordParser.transPassword(user);
        user.setPassword(s);
        mapper.insertSelective(user);
    }

    @Override
    public void updateUser(User user) {
        if(user.getUid() == null){
            throw new NullPointerException("uid");
        }
        String s = passwordParser.transPassword(user);
        user.setPassword(s);
        mapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User getByUsername(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        List<User> users = mapper.selectByExample(example);
        if(users != null && users.size() >= 1){
            User user1 = users.get(0);
            return user1;
        }else{
            return null;
        }
    }


}
