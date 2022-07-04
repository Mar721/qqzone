package com.qqzone.service.impl;

import com.qqzone.dao.TopicDao;
import com.qqzone.pojo.Reply;
import com.qqzone.pojo.Topic;
import com.qqzone.pojo.UserBasic;
import com.qqzone.service.ReplyService;
import com.qqzone.service.TopicService;
import com.qqzone.service.UserBasicService;

import java.util.List;

public class TopicServiceImpl implements TopicService {
    private TopicDao topicDao;
    private ReplyService replyService;
    private UserBasicService userBasicService;
    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {
        return topicDao.getTopicList(userBasic);
    }

    @Override
    public Topic getTopicById(Integer id) {
        Topic topicById = topicDao.getTopicById(id);
        UserBasic userBasicById = userBasicService.getUserBasicById(topicById.getAuthor().getId());
        topicById.setAuthor(userBasicById);
        List<Reply> replyList = replyService.getReplyList(topicById);
        topicById.setReplyList(replyList);
        return topicById;
    }

    @Override
    public void delTopicById(Integer id) {
        //根据id获取到topic
        //查询是否有关联的回复,有则删除
        //删除topic
        Topic topicById = topicDao.getTopicById(id);
        List<Reply> replyList = replyService.getReplyList(topicById);
        for (Reply reply : replyList) {
            replyService.delReply(reply.getId());
        }
        topicDao.delTopicById(id);
    }

    @Override
    public void addTopic(Topic topic) {
        topicDao.addTopic(topic);
    }

}
