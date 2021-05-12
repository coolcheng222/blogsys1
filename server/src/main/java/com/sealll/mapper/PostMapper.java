package com.sealll.mapper;

import com.sealll.application.post.bean.Post;
import com.sealll.application.post.bean.PostExample;
import com.sealll.application.tag.bean.Tag;
import org.apache.ibatis.annotations.Param;

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

    public Post getPostTerminalWithCountByPid(String pid);


    public List<Post> getPlainPostByExample(PostExample example);
    public List<Post> getPostWithAuthorByExample(PostExample example);
    public List<Post> getPostWithTagsByExample(PostExample example);
    public List<Post> getPostTerminalByExample(PostExample example);

    public List<Post> getPostTerminalWithCountByExample(PostExample example);

    public List<Post> getPostByTag(String tid);

    public boolean insertPost(Post post);
    public boolean insertTags(Post post);

    public boolean updateSelective(Post post);
    public boolean deleteAllTags(String pid);

    public boolean deletePostByPid(String pid);
}
