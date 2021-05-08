package com.sealll.application.post.service.impl;

import com.github.pagehelper.PageHelper;
import com.sealll.application.post.bean.Post;
import com.sealll.application.post.bean.PostExample;
import com.sealll.application.post.service.PostService;
import com.sealll.constant.PageConstants;
import com.sealll.mapper.PostMapper;
import org.apache.ibatis.datasource.DataSourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/7 23:36
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper mapper;
    @Override
    public void addPost(Post post) throws DataSourceException {
        boolean insertPost = mapper.insertPost(post);
        if(post.getTags().size() >= 1){
            boolean insertTags = mapper.insertTags(post);
        }

    }

    @Override
    public void deletePost(Post post) {
        boolean b = mapper.deletePostByPid(post.getpId());
    }

    @Override
    public void updatePost(Post post) {
        boolean b = mapper.updateSelective(post);
    }

    @Override
    public void updateTags(Post post) {
        mapper.deleteAllTags(post.getpId());
        mapper.insertTags(post);
    }

    @Override
    public List<Post> getPagedPostTermial(Integer pageNum) {
        PageHelper.startPage(pageNum, PageConstants.PAGESIZE);
        List<Post> list = mapper.getPostTerminalByExample(new PostExample());
        return list;
//        PageInfo pageInfo = new PageInfo(list);
//        Map<String,Object> res = new HashMap<>();
//        res.put("list",list);
//        res.put("page",pageInfo);
//        return Msg.success("").extend(res);
    }

    @Override
    public Post getPostByPid(String pid){
        Post post = mapper.getPostTerminalByPid(pid);
        return post;
    }

    @Override
    public String validatePost(Post post) {
        String title = post.getTitle();
        if(title == null || title.length() < 3){
            return "标题长度不够";
        }else{
            return null;
        }
    }

    @Override
    public List<Post> searchByTitle(String title,Integer pageNum){
        PostExample example = new PostExample();
        PostExample.Criteria criteria = example.createCriteria();
        criteria.andTitleLike("%" + title + "%");
        PageHelper.startPage(pageNum,PageConstants.PAGESIZE);
        List<Post> list = mapper.getPostTerminalByExample(example);
        return list;
    }

    @Override
    public List<Post> getByUid(String uid,Integer pageNum) {
        PostExample example = new PostExample();
        PostExample.Criteria criteria = example.createCriteria();
//        criteria.andTitleLike("%" + title + "%");
        criteria.andUidEqualTo(uid);
        PageHelper.startPage(pageNum,PageConstants.PAGESIZE);
        List<Post> list = mapper.getPostTerminalByExample(example);
        return list;
    }


}