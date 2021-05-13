package com.sealll.application.post.controller;

import com.github.pagehelper.PageInfo;
import com.sealll.application.post.bean.Post;
import com.sealll.application.post.service.PostService;
import com.sealll.application.tag.bean.Tag;
import com.sealll.bean.Msg;
import com.sealll.constant.OrderClause;
import com.sealll.exception.ClauseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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
    public Msg getByUid(@PathVariable("uid") String uid, @RequestParam(value="page",defaultValue = "1",required = false) Integer page) {
        List<Post> list = postService.getByUid(uid, page);
        if (list != null) {
            PageInfo pageInfo = new PageInfo(list);
            return Msg.success("").extend(pageInfo);
        } else {
            return Msg.fail("该用户还没发过文章");
        }
    }

    //to test
    @GetMapping
    public Msg getByPage(@RequestParam(value="page",defaultValue = "1",required = false) Integer page,
                         @RequestParam(value="clause",defaultValue = "time",required = false)String clause,
                         @RequestParam(value="asc",defaultValue = "true",required = false)Boolean asc) throws ClauseNotFoundException {
        boolean contains = OrderClause.clauses.contains(clause);
        if(!contains){
            throw new ClauseNotFoundException("bad order clause");
        }
        if(!asc){
            clause += " desc";
        }
        List<Post> list = postService.getPagedPostTermialOrder(page,clause);
        if (list != null) {
            PageInfo pageInfo = new PageInfo(list);
            return Msg.success("").extend(pageInfo);
        } else {
            return Msg.fail("没有相关内容");
        }
    }


    @GetMapping("/title/{title}")
    public Msg getByTitleSearch(@PathVariable("title") String title,@RequestParam(value="page",defaultValue = "1",required = false) Integer page){
        List<Post> list = postService.searchByTitle(title,page);
        if (list != null) {
            PageInfo pageInfo = new PageInfo(list);
            return Msg.success("").extend(pageInfo);
        } else {
            return Msg.fail("没有相关内容");
        }
    }
    //to test
    @GetMapping("/hint/{title}")
    public Msg getHintTitleSearch(@PathVariable("title") String title){
        List<Post> list = postService.hintByTitle(title);
        if (list != null) {
            return Msg.success("").extend(list);
        } else {
            return Msg.fail("没有相关内容");
        }
    }

    @GetMapping("/tag/{tid}")
    public Msg getByTag(@PathVariable("tid")String tid,@RequestParam(value="page",defaultValue = "1",required = false)Integer page){
        List<Post> list = postService.getPostByTag(tid,page);
        if (list != null) {
            PageInfo pageInfo = new PageInfo(list);
            return Msg.success("").extend(pageInfo);
        } else {
            return Msg.fail("没有相关内容");
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
