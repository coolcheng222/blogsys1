package com.sealll.application.reply.controller;

import com.github.pagehelper.PageInfo;
import com.sealll.application.reply.bean.Reply;
import com.sealll.application.reply.service.ReplyService;
import com.sealll.bean.Msg;
import com.sealll.constant.ParameterConstants;
import com.sealll.utils.SelfChecker;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/13 21:08
 */
@RestController
@RequestMapping("/reply")
public class ReplyController {
    //    public boolean addReply(Reply reply);
    @Autowired
    private ReplyService replyService;

    @PostMapping
    public Msg addReply(@RequestBody Reply reply) {
        String uid = reply.getUser().getUid();
        Object o = SecurityUtils.getSubject().getSession().getAttribute(ParameterConstants.UID_SESSION_KEY);
        if(o == null){
            return Msg.auth("先登录");
        }else{
            if(!((String)o).equals(uid)){
                return Msg.permit("没有权限");
            }
        }
        boolean b = replyService.addReply(reply);
        if (b) {
            return Msg.success("回复成功");
        } else {
            return Msg.fail("回复失败");
        }
    }

    //    public boolean deleteReply(String reid);
    @DeleteMapping
    public Msg deleteReply(@RequestParam("reid") String reid,@RequestParam("uid")String uid,@RequestParam("pid")String pid) {
        boolean check = SelfChecker.check(uid);
        if(!check){
            return Msg.permit("无权限");
        }
        boolean b = replyService.delete2Reply(reid,uid,pid);
        if (b) {
            return Msg.success("删除成功");
        } else {
            return Msg.fail("删除失败");
        }
    }
//    public List<Reply> getPostReplyListOrder(String uid, String pid,
    @GetMapping("/post/{pid}")
    public Msg getPostReplyListOrder(@RequestParam("uid")String uid,
                                     @PathVariable("pid")String pid,
                                     @RequestParam(value="page",required = false,defaultValue = "1")Integer page,
                                     @RequestParam(value="clause",required = false,defaultValue = "time desc")String clause){
        List<Reply> list = replyService.getPostReplyListOrder(uid, pid, clause, page);
        if(list == null){
            return Msg.fail("本文无回复");
        }else{
            PageInfo<Reply> pageInfo = new PageInfo<>(list);
            return Msg.success("").extend(pageInfo);
        }
    }
//    public List<Reply> getUserReplyList(String uid,Integer page);
    @GetMapping("/user/{uid}")
    public Msg getUserReplyList(@PathVariable("uid") String uid,@RequestParam(value="page",required = false,defaultValue = "1") Integer page){
        List<Reply> list = replyService.getUserReplyList(uid,page);
        if(list == null){
            return Msg.fail("该用户无回复");
        }else{
            PageInfo<Reply> pageInfo = new PageInfo<>(list);
            return Msg.success("").extend(pageInfo);
        }
    }
//    public List<Reply> getAllReplyListOrder();
    @GetMapping("/all")
    public Msg getAllReplyListOrder(){
        List<Reply> list = replyService.getAllReplyListOrder();
        if(list == null){
            return Msg.fail("全版面无回复");
        }else{
            PageInfo<Reply> pageInfo = new PageInfo<>(list);
            return Msg.success("").extend(pageInfo);
        }
    }
}
