package com.qqzone.dao;

import com.qqzone.pojo.Topic;
import com.qqzone.pojo.UserBasic;

import java.util.List;

public interface TopicDao {
    //跟据用户信息获取日志列表
    List<Topic> getTopicList(UserBasic userBasic);
    //增加日志
    void addTopic(Topic topic);
    //删除日志
    void delTopicById(Integer id);
    //获取特定日志信息
    Topic getTopicById(Integer id);
}
