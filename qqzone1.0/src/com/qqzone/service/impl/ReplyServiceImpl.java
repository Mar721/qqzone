package com.qqzone.service.impl;

import com.qqzone.dao.ReplyDao;
import com.qqzone.pojo.HostReply;
import com.qqzone.pojo.Reply;
import com.qqzone.pojo.Topic;
import com.qqzone.pojo.UserBasic;
import com.qqzone.service.HostReplyService;
import com.qqzone.service.ReplyService;
import com.qqzone.service.TopicService;
import com.qqzone.service.UserBasicService;

import java.util.List;

public class ReplyServiceImpl implements ReplyService {
    private ReplyDao replyDao;
    private HostReplyService hostReplyService;
    private UserBasicService userBasicService;
    @Override
    public List<Reply> getReplyList(Topic topic) {
        List<Reply> replyList = replyDao.getReplyList(topic);
        for (Reply reply : replyList) {
            //获取回复相关的作者和主人回复
            UserBasic userBasicById = userBasicService.getUserBasicById(reply.getAuthor().getId());
            HostReply hostReply = hostReplyService.getHostReply(reply);
            reply.setAuthor(userBasicById);
            reply.setHostReply(hostReply);

        }
        return replyList;
    }

    @Override
    public void addReply(Reply reply) {
        replyDao.addReply(reply);
    }

    @Override
    public void delReply(Integer id) {
        //根据id获取到reply
        //如果reply有关联的hostReply,则先删除hostReply
        //删除reply
        Reply replyById = replyDao.getReplyById(id);
        if (replyById!=null){
            HostReply hostReply = hostReplyService.getHostReply(replyById);
            if (hostReply!=null){
                hostReplyService.delHostReply(hostReply);
            }
        }
        replyDao.delReplyById(id);
    }

    @Override
    public Integer getTopicId(Integer replyId) {
        Reply replyById = replyDao.getReplyById(replyId);
        return replyById.getTopic().getId();
    }
}
