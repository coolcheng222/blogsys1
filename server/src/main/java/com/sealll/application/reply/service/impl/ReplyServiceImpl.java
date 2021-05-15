package com.sealll.application.reply.service.impl;

import com.github.pagehelper.PageHelper;
import com.sealll.application.reply.bean.Reply;
import com.sealll.application.reply.service.ReplyService;
import com.sealll.constant.PageConstants;
import com.sealll.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author sealll
 * @time 2021/5/13 20:46
 */
@Service
@Async
@Transactional
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyMapper replyMapper;
    @Override
    public boolean addReply(Reply reply) {
        return replyMapper.addReply(reply);
    }

    @Override
    public boolean deleteReply(String reid,String pid) {
        return replyMapper.deleteReply(reid,pid);
    }

    @Override
    public boolean delete2Reply(String reid, String uid, String pid) {
        return replyMapper.delete2Reply(reid,uid,pid);
    }

    @Override
    public List<Reply> getPostReplyListOrder(String uid, String pid, String clause,Integer page) {
        PageHelper.startPage(page, PageConstants.PAGESIZE);
        List<Reply> list = replyMapper.getPostReplyListOrder(uid, pid, clause);
        return list;
    }

    @Override
    public List<Reply> getUserReplyList(String uid,Integer page) {
        PageHelper.startPage(page, PageConstants.PAGESIZE);
        List<Reply> list = replyMapper.getUserReplyList(uid);
        return list;
    }

    @Override
    public List<Reply> getAllReplyListOrder() {
        PageHelper.startPage(1,PageConstants.SMALLPAGE);
        List<Reply> list = replyMapper.getAllReplyListOrder();
        return list;
    }
}
