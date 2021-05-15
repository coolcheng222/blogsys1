package com.sealll.application.history.controller;

import com.sealll.application.history.service.HistoryService;
import com.sealll.application.post.bean.Post;
import com.sealll.bean.Msg;
import com.sealll.utils.ResultHandler;
import com.sealll.utils.SelfChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author sealll
 * @time 2021/5/15 22:42
 */
@Controller
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @GetMapping("/post")
    public Msg getPostHistory(@RequestParam String uid){
        boolean check = SelfChecker.check(uid);
        if(!check){
            return Msg.permit("没有权限查看");
        }
        List<Post> postHistroy = historyService.getPostHistroy(uid);
        return ResultHandler.handle(postHistroy, Objects::isNull,"没有浏览记录",postHistroy);
    }
    @GetMapping("/search")
    public Msg getSearchHistory(@RequestParam String uid){
        boolean check = SelfChecker.check(uid);
        if(!check){
            return Msg.permit("没有权限查看");
        }
        Set<String> postHistroy = historyService.getSearchHistory(uid);
        return ResultHandler.handle(postHistroy, Objects::isNull,"",postHistroy);
    }
}
