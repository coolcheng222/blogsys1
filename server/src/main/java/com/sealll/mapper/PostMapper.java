package com.sealll.mapper;

import com.sealll.application.post.bean.Post;
import com.sealll.application.post.bean.PostExample;
import com.sealll.application.tag.bean.Tag;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/7 15:46
 */
public interface PostMapper {
    public Post getPlainPostByPid(String pid);
    public Post getPostWithAuthorByPid(String pid);
    public Post getPostWithTagsByPid(String pid);
    public Post getPostTerminalByPid(String pid);

    public Post getPlainPostByExample(PostExample example);
    public Post getPostWithAuthorByExample(PostExample example);
    public Post getPostWithTagsByExample(PostExample example);
    public Post getPostTerminalByExample(PostExample example);

    public boolean insertPost(Post post);
    public boolean insertTags(Post post);

    public boolean updateSelective(Post post);
    public boolean deleteAllTags(String pid);

    public boolean deletePostByPid(String pid);
}
