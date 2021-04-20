package com.sealll.shiro.md5;

import com.sealll.user.bean.User;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Component;

/**
 * @author sealll
 * @time 2021/4/16 20:07
 */
@Component
public class PasswordParser {
    public String transPassword(User user) {
        String password = user.getPassword();
        String username = user.getUsername();
        return transPassword(username,password);
    }
    public String transPassword(String username,String password){
        if (password != null) {
            String s = new Md5Hash(password, username).toString();
            return s;
        } else {
            return null;
        }
    }
}
