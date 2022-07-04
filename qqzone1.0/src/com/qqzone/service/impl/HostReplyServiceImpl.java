package com.qqzone.service.impl;

import com.qqzone.dao.HostReplyDao;
import com.qqzone.pojo.HostReply;
import com.qqzone.pojo.Reply;
import com.qqzone.pojo.Topic;
import com.qqzone.service.HostReplyService;

public class HostReplyServiceImpl implements HostReplyService {
    private HostReplyDao hostReplyDao;
    @Override
    public HostReply getHostReply(Reply reply) {
        return hostReplyDao.getHostReply(reply);
    }

    @Override
    public void delHostReply(HostReply hostReply) {
        hostReplyDao.delHostReply(hostReply);
    }

    @Override
    public void addHostReply(HostReply hostReply) {
        hostReplyDao.addHostReply(hostReply);
    }
}
