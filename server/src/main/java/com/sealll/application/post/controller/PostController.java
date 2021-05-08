package com.sealll.application.post.controller;

import com.github.pagehelper.PageInfo;
import com.sealll.application.post.bean.Post;
import com.sealll.application.post.service.PostService;
import com.sealll.bean.Msg;
import com.sealll.bean.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sealll
 * @time 2021/4/28 22:26
 */
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{pid}")
    public Msg readPost(@PathVariable("pid") String pid) {
        Post post = postService.getPostByPid(pid);
        if (post == null) {
            return Msg.fail("文章不存在");
        } else {
            return Msg.success("").extend(post);
        }
    }

    @GetMapping("/user/{uid}")
    public Msg getByUid(@PathVariable("uid") String uid, @RequestParam("page") Integer page) {
        List<Post> list = postService.getByUid(uid, page);
        if (list != null) {
            PageInfo pageInfo = new PageInfo(list);
            Page page1 = new Page(list, pageInfo);
            return Msg.success("").extend(page1);
        } else {
            return Msg.fail("该用户还没发过文章");
        }
    }

    @GetMapping
    public Msg getByPage(@RequestParam("page") Integer page){
        List<Post> list = postService.getPagedPostTermial(page);
        if (list != null) {
            PageInfo pageInfo = new PageInfo(list);
            Page page1 = new Page(list, pageInfo);
            return Msg.success("").extend(page1);
        } else {
            return Msg.fail("未知错误,联系管理员");
        }
    }

    @GetMapping("/title/{title}")
    public Msg getByTitleSearch(@PathVariable("title") String title,@RequestParam("page") Integer page){
        List<Post> list = postService.searchByTitle(title,page);
        if (list != null) {
            PageInfo pageInfo = new PageInfo(list);
            Page page1 = new Page(list, pageInfo);
            return Msg.success("").extend(page1);
        } else {
            return Msg.fail("未知错误,联系管理员");
        }
    }


    @PostMapping
    public Msg addPost(@RequestBody Post post) {
        String s = postService.validatePost(post);
        if(s != null){
            return Msg.fail(s);
        }
        try {
            postService.addPost(post);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail("增加失败");
        }
        return Msg.success("增加成功");
    }

    @DeleteMapping
    public Msg deletePost(@RequestBody Post post){
        try {
            postService.deletePost(post);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail("");
        }
        return Msg.success("删除成功");
    }

    @PutMapping
    public Msg updatePost(@RequestBody Post post){
        try {
            postService.updatePost(post);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail("修改失败");
        }
        return Msg.success("修改成功");
    }
    @PutMapping("/tags")
    public Msg updateTag(@RequestBody Post post){
        try{
            postService.updateTags(post);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail("");
        }
        return Msg.success("修改成功");
    }

}