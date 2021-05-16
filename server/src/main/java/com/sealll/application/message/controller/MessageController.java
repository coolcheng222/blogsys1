package com.sealll.application.message.controller;

import com.github.pagehelper.PageInfo;
import com.sealll.application.message.bean.Message2;
import com.sealll.application.message.service.MessageService;
import com.sealll.bean.Msg;
import com.sealll.utils.ResultHandler;
import com.sealll.utils.SelfChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author sealll
 * @time 2021/5/16 18:57
 */
@RestController
@RequestMapping("msg")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @GetMapping("/{uid}")
    public Msg getBeforeTime(@PathVariable("uid") String uid,@RequestParam("before") Long time,
                             @RequestParam(value = "page",required = false,defaultValue = "1")Integer page){
        boolean check = SelfChecker.check(uid);
        if(!check){
            return Msg.permit("没有权限");
        }
        List<Message2> list = messageService.getMessageBeforeTime(uid, time, page);
        Msg msg = ResultHandler.handle(list, Objects::isNull, "没有消息", new PageInfo<>(list));
        return msg;
    }
    @DeleteMapping("/{mid}")
    public Msg deleteMsg(@PathVariable("mid")String mid){
        String uid = messageService.getByMid(mid).getUid();
        boolean check = SelfChecker.check(uid);
        if(!check){
            return Msg.permit("没有权限");
        }
        boolean b = messageService.deleteMessage(mid);
        if(!b){
            return Msg.fail("删除失败");
        }else{
            return Msg.success("删除成功");
        }
    }
}
