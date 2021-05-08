package com.sealll.application.post.service;

import com.sealll.application.post.bean.Post;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/7 23:36
 */
public interface PostService {
    public void addPost(Post post) throws Exception;
    public void deletePost(Post post)  throws Exception;
    public void updatePost(Post post) throws Exception;

    public void updateTags(Post post) throws Exception;

    public List<Post> getPagedPostTermial(Integer pageNum);

    public List<Post> searchByTitle(String title,Integer pageNum);

    public List<Post> getByUid(String uid,Integer pageNum);

    public Post getPostByPid(String pid);

    public String validatePost(Post post);

//    public Post getPlainPostDescription(String )
}