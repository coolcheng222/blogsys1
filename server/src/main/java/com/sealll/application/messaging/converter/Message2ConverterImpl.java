package com.sealll.application.messaging.converter;

import com.sealll.application.message.bean.Message2;
import com.sealll.application.post.bean.Post;
import com.sealll.application.post.service.PostService;
import com.sealll.application.reply.bean.Reply;
import com.sealll.application.role.bean.Role;
import com.sealll.application.role.service.RoleService;
import com.sealll.application.user.bean.User;
import com.sealll.application.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

/**
 * @author sealll
 * @time 2021/5/17 10:56
 */
@Component
@Transactional
public class Message2ConverterImpl implements Message2Converter {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public Message2 convert(Reply reply) {
        String pid = reply.getPost().getpId();
        Post post = postService.getPostByPid(pid);
        User byUid = userService.getByUid(reply.getUser());
        if(post != null & byUid != null){

            Message2 message2 = new Message2();
            message2.setUid(post.getAuthor().getUid());
            if(!post.getAuthor().getUid().equals(byUid.getUid())){
                message2.setMtitle("您的文章" + "<b>"+post.getTitle()+"</b>被 "+byUid.getUsername()+" 回复了");
                message2.setContent(reply.getContent());
                message2.setTime(new Date());
                return message2;
            }else{
                return null;
            }

        }
        return null;
    }

    @Override
    public Message2 convert(Role role) {
        String username = role.getUsername();
        Role role1 = roleService.getRole(username);
        User user = new User();
        user.setUsername(username);
        User handler = userService.getByUsername(user);
        Message2 message2 = new Message2();
        message2.setUid(handler.getUid());
        message2.setTime(new Date());
        message2.setMtitle("您的权限状态发生改变");
        if(role1.getRole().equals("baned")){
            message2.setContent("封禁,至 " + role1.getTtl());
        }else{
            message2.setContent(role1.getRole());
        }
        return message2;
    }

    @Override
    public Message2 convert(String subber, String subbed) {
        Message2 message2 = new Message2();
        message2.setUid(subbed);
        User user = new User();
        user.setUid(subber);
        User byUid = userService.getByUid(user);
        if(byUid != null){
            message2.setMtitle("您被 <b>"+ byUid.getUsername() +"</b> 关注了");
            message2.setContent("");
            message2.setTime(new Date());
            return message2;
        }
        return null;
    }
}
