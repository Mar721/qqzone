package com.qqzone.dao.impl;


import com.myssm.basedao.BaseDAO;
import com.qqzone.dao.ReplyDao;
import com.qqzone.pojo.Reply;
import com.qqzone.pojo.Topic;

import java.util.List;

public class ReplyDaoImpl extends BaseDAO<Reply> implements ReplyDao {
    @Override
    public List<Reply> getReplyList(Topic topic) {
        return super.executeQuery("select * from t_reply where topic=?;",topic.getId());
    }

    @Override
    public Reply getReplyById(Integer id) {
        return super.load("select * from t_reply where id=?;",id);
    }

    @Override
    public void addReply(Reply reply) {
        super.executeUpdate("insert into t_reply(content,replyDate,author,topic)values(?,?,?,?);",
                reply.getContent(),reply.getReplyDate(),reply.getAuthor().getId(),reply.getTopic().getId());
    }

    @Override
    public void delReplyById(Integer id) {
        super.executeUpdate("delete from t_reply where id=?;",id);
    }
}
