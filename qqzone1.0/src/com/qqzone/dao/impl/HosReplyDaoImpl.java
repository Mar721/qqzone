package com.qqzone.dao.impl;

import com.myssm.basedao.BaseDAO;
import com.qqzone.dao.HostReplyDao;
import com.qqzone.pojo.HostReply;
import com.qqzone.pojo.Reply;

public class HosReplyDaoImpl extends BaseDAO<HostReply> implements HostReplyDao {
    @Override
    public HostReply getHostReply(Reply reply) {
        return super.load("select * from t_host_reply where reply=?",reply.getId());
    }

    @Override
    public void delHostReply(HostReply hostReply) {
        super.executeUpdate("delete from t_host_reply where id=?;",hostReply.getId());
    }

    @Override
    public void addHostReply(HostReply hostReply) {
        super.executeUpdate("insert into t_Host_reply(content,HostReplyDate,author,reply)values(?,?,?,?);",
                hostReply.getContent(),hostReply.getHostReplyDate(),hostReply.getAuthor().getId(),hostReply.getReply().getId());
    }
}
