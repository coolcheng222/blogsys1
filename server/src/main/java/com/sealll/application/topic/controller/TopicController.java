package com.sealll.application.topic.controller;

import com.sealll.application.post.bean.Post;
import com.sealll.application.topic.service.TopicService;
import com.sealll.bean.Msg;
import com.sealll.utils.ResultHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author sealll
 * @time 2021/5/17 22:05
 */
@RestController
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping("/topic/{uid}")
    public Msg get(@PathVariable("uid")String uid,
                   @RequestParam(value="page",required = false,defaultValue = "1")Integer page,
                   @RequestParam("before")Long timestamp){
        List<Post> posts = topicService.getPosts(uid, page, timestamp);
        Msg msg = ResultHandler.handle(posts, Objects::isNull, "没有内容", posts);
        return msg;
    }
}
