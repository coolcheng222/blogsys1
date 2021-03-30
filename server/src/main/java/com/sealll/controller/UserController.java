package com.sealll.controller;

import com.sealll.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sealll
 * @time 2021/3/26 20:47
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String getPage(){

    }
}
