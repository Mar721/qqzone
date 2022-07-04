package com.qqzone.dao;

import com.qqzone.pojo.HostReply;
import com.qqzone.pojo.Reply;

public interface HostReplyDao {
    //通过回复获取主人回复
    HostReply getHostReply(Reply reply);
    //删除主人回复
    void delHostReply(HostReply hostReply);
    //增加主人回复
    void addHostReply(HostReply hostReply);
}
