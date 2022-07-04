package com.qqzone.dao.impl;


import com.myssm.basedao.BaseDAO;
import com.qqzone.dao.TopicDao;
import com.qqzone.pojo.Topic;
import com.qqzone.pojo.UserBasic;

import java.util.List;

public class TopicDaoImpl extends BaseDAO<Topic> implements TopicDao {
    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {
        return super.executeQuery("select * from t_topic where author=?;", userBasic.getId());
    }

    @Override
    public void addTopic(Topic topic) {
        super.executeUpdate("insert into t_topic(title,content,topicDate,author)values(?,?,?,?);",topic.getTitle(),topic.getContent(),topic.getTopicDate(),topic.getAuthor().getId());
    }

    @Override
    public void delTopicById(Integer id) {
        super.executeUpdate("delete from t_topic where id=?;",id);
    }

    @Override
    public Topic getTopicById(Integer id) {
        return super.load("select * from t_topic where id=?;", id);

    }
}
