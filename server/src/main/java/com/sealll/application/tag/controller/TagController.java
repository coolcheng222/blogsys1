package com.sealll.application.tag.controller;

import com.github.pagehelper.PageInfo;
import com.sealll.application.post.bean.Post;
import com.sealll.application.tag.bean.Tag;
import com.sealll.application.tag.service.TagService;
import com.sealll.bean.Msg;
import com.sealll.constant.PageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/9 19:26
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/{tid}")
    public Msg getByTid(@PathVariable("tid") String tid){
        Tag tag = tagService.getPostCount(tid);
        if(tag == null){
            return Msg.fail("没有对应tag");
        }else{
            return Msg.success("").extend(tag);
        }
    }
    @GetMapping("/name/{tagname}")
    public Msg getByName(@PathVariable("tagname") String tagname){
        List<Tag> tags = tagService.searchByTagname(tagname);
        if(tags == null || tags.size() == 0){
            return Msg.fail("没有对应字段");
        }else{
            return Msg.success("").extend(tags);
        }
    }
    @GetMapping("/tags")
    public Msg getTags(){
        List<Tag> tags = tagService.selectPostCount();
        if(tags == null || tags.size() == 0){
            return Msg.fail("没有tag");
        }else{
            return Msg.success("").extend(tags);
        }
    }

    @GetMapping("/order")
    public Msg getTagsByOrder(){
        List<Tag> tags = tagService.orderByPostCount(PageConstants.TAGSIZE);
        if(tags == null || tags.size() == 0){
            return Msg.fail("没有tag");
        }else{
            return Msg.success("").extend(tags);
        }
    }

   /* @GetMapping("/post/{tid}")
    public Msg getPostByTid(@PathVariable("tid")String tid,
            @RequestParam(value="page",required = false,defaultValue = "1")Integer page){
        Tag tags = tagService.selectPostByTid(tid,page);
        if(tags == null){
            return Msg.fail("没有tag");
        }else{
            PageInfo<Post> info = new PageInfo<>(tags.getList());
            return Msg.success("").extend(info);
        }
    }*/

    @PostMapping
    public Msg addTag(@RequestBody Tag tag) {
        boolean b = false;
        try {
            b = tagService.addTag(tag);

        }catch (DuplicateKeyException e){
            return Msg.fail("该tag已存在");
        }
        if (b) {
            return Msg.success("添加成功");
        } else {
            return Msg.fail("添加失败");
        }
    }

    @PutMapping
    public Msg updateTag(@RequestBody Tag tag){
        boolean b = false;
        try {
            b = tagService.updateTag(tag);

        }catch (DuplicateKeyException e){
            return Msg.fail("该tag已存在");
        }
        if(b){
            return Msg.success("修改成功");
        }else{
            return Msg.fail("修改失败");
        }
    }

    @DeleteMapping
    public Msg deleteMapping(@RequestBody Tag tag){
        boolean b = tagService.deleteTag(tag);
        if(b){
            return Msg.success("删除成功");
        }else{
            return Msg.fail("删除失败");
        }
    }

}
