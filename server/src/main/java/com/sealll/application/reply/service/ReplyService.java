package com.sealll.application.reply.service;

import com.sealll.application.reply.bean.Reply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/13 20:45
 */
public interface ReplyService {
    public boolean addReply(Reply reply);
    public boolean deleteReply(String reid,String pid);
    public boolean delete2Reply(String reid,String uid,String pid);

    /**
     *
     * @param uid <u>viewer</u>
     * @param pid
     * @param clause order by clause
     * @return
     */
    public List<Reply> getPostReplyListOrder(String uid, String pid,
                                             String clause,Integer page);
    public List<Reply> getUserReplyList(String uid,Integer page);

    public List<Reply> getAllReplyListOrder();
}
