package com.sealll.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author sealll
 * @time 2021/3/26 20:47
 */
@Controller
public class UserController {
    @RequestMapping("/handle")
    public String handle01(){
        return "success";
    }
}
