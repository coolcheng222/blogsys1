package com.sealll.application.sub.controller;

import com.sealll.application.sub.bean.Subscribe;
import com.sealll.application.sub.service.SubscribeService;
import com.sealll.bean.Msg;
import com.sealll.utils.ResultHandler;
import com.sealll.utils.SelfChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author sealll
 * @time 2021/5/16 13:08
 */
@RestController
@RequestMapping("/sub")
public class SubscribeController {
    @Autowired
    private SubscribeService subscribeService;
    @PostMapping
    public Msg addSub(@RequestParam("subber") String subber,@RequestParam("subbed") String subbed){
        if(subber.equals(subbed)){
            return Msg.fail("不能关注自己");
        }
        boolean check = SelfChecker.check(subber);
        if(!check){
            return Msg.permit("没有权限");
        }
        boolean b = subscribeService.addSub(subber, subbed);
        if(b){
            return Msg.success("关注成功");
        }else{
            return Msg.fail("关注失败");
        }

    }
    @DeleteMapping
    public Msg deleteSub(@RequestParam("subber") String subber,@RequestParam("subbed") String subbed){
        if(subber.equals(subbed)){
            return Msg.fail("不能关注自己");
        }
        boolean check = SelfChecker.check(subber);
        if(!check){
            return Msg.permit("没有权限");
        }
        boolean b = subscribeService.deleteSub(subber, subbed);
        if(b){
            return Msg.success("取消关注成功");
        }else{
            return Msg.fail("取消关注失败");
        }
    }
    @GetMapping("/check")
    public Msg checkSub(@RequestParam("subber") String subber,@RequestParam("subbed") String subbed){
        if(subber.equals(subbed)){
            return Msg.fail("不能关注自己");
        }
        boolean b = subscribeService.checkSub(subber, subbed);
        if(b){
            return Msg.success("");
        }else{
            return Msg.fail("");
        }
    }
    @GetMapping("/subing/{uid}")
    public Msg getSubing(@PathVariable("uid") String uid){
        Subscribe subing = subscribeService.getSubing(uid);
        return ResultHandler.handle(subing, Objects::isNull,"没有信息",subing);
    }
    @GetMapping("/fans/{uid}")
    public Msg getFans(@PathVariable("uid") String uid){
        Subscribe subing = subscribeService.getFans(uid);
        return ResultHandler.handle(subing, Objects::isNull,"没有信息",subing);
    }
}
