package com.sealll.application.user.utils;

import com.sealll.application.user.bean.User;
import org.springframework.stereotype.Service;

/**
 * @author sealll
 * @time 2021/4/20 10:45
 */
@Service
public class UserValidator {
    private String emailR = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
    private String usernameR = "^[a-zA-Z0-9_]{5,18}$";
    public String userValidate(User user){
        String email = user.getEmail();
        String username = user.getUsername();
        String password = user.getPassword();
        if(email == null || !email.matches(emailR)){
            return "email format error";
        }
        if(username == null || !username.matches(usernameR)){
            return "username should be 5-10 length alpha or digit or _";
        }
        if(password == null || !password.matches(usernameR)){
            return "password should be 5-10 length alpha or digit or _";
        }
        return null;
    }
    public String updateValidate(User user){
        String email = user.getEmail();
        String username = user.getUsername();
        String password = user.getPassword();
        if(email != null && !email.matches(emailR)){
            return "email format error";
        }
        if(password != null && !password.matches(usernameR)){
            return "password should be 5-10 length alpha or digit or _";
        }
        return null;
    }
}
