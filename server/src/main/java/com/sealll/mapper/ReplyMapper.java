package com.sealll.mapper;

import com.sealll.application.post.bean.Post;
import com.sealll.application.reply.bean.Reply;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/13 16:11
 */
public interface ReplyMapper {
    public boolean addReply(Reply reply);
    public boolean deleteReply(@Param("reid") String reid, @Param("pid") String pid);

    /**
     *
     * @param uid <u>viewer</u>
     * @param pid
     * @param clause order by clause
     * @return
     */
    public List<Reply> getPostReplyListOrder(@Param("uid") String uid,
                                         @Param("pid") String pid,
                                             @Param("clause")String clause);
    public List<Reply> getUserReplyList(String uid);

    public List<Reply> getAllReplyListOrder();


}
