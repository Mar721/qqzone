package com.qqzone.service;

import com.qqzone.pojo.HostReply;
import com.qqzone.pojo.Reply;
import com.qqzone.pojo.Topic;

public interface HostReplyService {
    //根据回复获取主人回复
    HostReply getHostReply(Reply reply);
    //删除主人回复
    void delHostReply(HostReply hostReply);
    //增加主人回复
    void addHostReply(HostReply hostReply);
}
