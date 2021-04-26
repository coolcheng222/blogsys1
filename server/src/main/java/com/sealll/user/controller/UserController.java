package com.sealll.user.controller;

import com.sealll.bean.Msg;
import com.sealll.constant.Router;
import com.sealll.user.bean.User;
import com.sealll.user.service.UserService;
import com.sealll.user.utils.UserValidator;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sealll
 * @time 2021/3/26 20:47
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserValidator validator;

    @ResponseBody
    @GetMapping("/login")
    public Msg getLoginPage(){
        return Msg.redirect(Router.LOGIN);
    }

    @ResponseBody
    @PostMapping("/login")
    public Msg login(HttpServletRequest request){
        Object failure = request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);

        if (failure == null) {
            return Msg.redirect(Router.INDEX);
        }else{
            return Msg.fail("login fail, username or password error");
        }
    }

    @GetMapping("/register")
    public Msg getRegisterPage(){
        return Msg.redirect(Router.REG);
    }
    @PostMapping("/register")
    public Msg register(User user){
        String s = validator.userValidate(user);
        User byUsername = userService.getByUsername(user);
        if(byUsername != null){
            return Msg.fail("用户名已存在");
        }
        if(s != null){
            return Msg.fail(s);
        }else{
            user.setUid(" ");
            userService.addUser(user);
            return Msg.redirect("/login");
        }
    }

    @GetMapping("/user/{username}")
    public Msg checkName(@PathVariable("username") String username){
        User user = new User();
        user.setUsername(username);
        User byUsername = userService.getByUsername(user);
        if(byUsername == null){
            return Msg.success("");
        }else{
            return Msg.fail("用户名已存在");
        }

    }
}
