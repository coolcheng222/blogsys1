package com.sealll.application.search.controller;

import com.sealll.bean.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author sealll
 * @time 2021/5/14 10:17
 */
@Controller
public class SearchController {
    @RequestMapping("/search")
    public Msg record(@RequestParam("title") String title){
        return Msg.success("");
    }
}
