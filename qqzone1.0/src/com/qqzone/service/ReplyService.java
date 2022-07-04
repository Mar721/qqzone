package com.qqzone.service;


import com.qqzone.pojo.Reply;
import com.qqzone.pojo.Topic;

import java.util.List;

public interface ReplyService {
    //根据日志信息获取所有回复
    List<Reply> getReplyList(Topic topic);
    //增加回复
    void addReply(Reply reply);
    //删除回复
    void delReply(Integer id);

    Integer getTopicId(Integer replyId);
}
