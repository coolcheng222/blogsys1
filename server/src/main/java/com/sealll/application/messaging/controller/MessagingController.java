package com.sealll.application.messaging.controller;

import com.sealll.application.message.bean.Message2;
import com.sealll.application.messaging.service.MessagingService;
import com.sealll.bean.Msg;
import com.sealll.utils.ResultHandler;
import com.sealll.utils.SelfChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author sealll
 * @time 2021/5/17 10:42
 */
@RestController
@RequestMapping("/unread")
public class MessagingController {
    @Autowired
    private MessagingService messagingService;
    @GetMapping("/count/{uid}")
    public Msg getCount(@PathVariable("uid")String uid){
        boolean check = SelfChecker.check(uid);
        if(!check){
            return Msg.permit("没有权限");
        }
        Integer res = messagingService.getCount(uid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", res);
        return Msg.success("map");
    }

    @GetMapping("/{uid}")
    public Msg getUnread(@PathVariable("uid") String uid){
        boolean check = SelfChecker.check(uid);
        if(!check){
            return Msg.permit("没有权限");
        }
        List<Message2> list = messagingService.getUnread(uid);
        Msg msg = ResultHandler.handle(list, Objects::isNull, "没有消息", list);
        return msg;
    }
}
