package com.qqzone.service;

import com.qqzone.pojo.Topic;
import com.qqzone.pojo.UserBasic;

import java.util.List;

public interface TopicService {
    //根据用户信息查看日志列表
    List<Topic> getTopicList(UserBasic userBasic);
    //根据日志id查询日志内容
    Topic getTopicById(Integer id);
    //根据id删除日志
    void delTopicById(Integer id);
    //增加日志
    void addTopic(Topic topic);
}
