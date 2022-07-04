package com.qqzone.dao;

import com.qqzone.pojo.Reply;
import com.qqzone.pojo.Topic;

import java.util.List;

public interface ReplyDao {
    //获取指定日志的回复列表
    List<Reply> getReplyList(Topic topic);
    //根据id获取reply
    Reply getReplyById(Integer id);
    //增加回复
    void addReply(Reply reply);
    //删除回复
    void delReplyById(Integer id);
    //
}
