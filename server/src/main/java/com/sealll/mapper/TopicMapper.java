package com.sealll.mapper;

import com.sealll.application.post.bean.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/17 21:26
 */
public interface TopicMapper {
    public List<Post> getPosts(@Param("uid")String uid,@Param("timestamp")Long timestamp);
}
