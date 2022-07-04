package com.qqzone.controllers;

import com.qqzone.pojo.HostReply;
import com.qqzone.pojo.Reply;
import com.qqzone.pojo.Topic;
import com.qqzone.pojo.UserBasic;
import com.qqzone.service.HostReplyService;
import com.qqzone.service.ReplyService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class ReplyController {
    private ReplyService replyService;
    private HostReplyService hostReplyService;
    public String addReply(String content, Integer topId,HttpSession session){
        Reply reply=new Reply(content,LocalDateTime.now(),
                (UserBasic) session.getAttribute("userBasic"),new Topic(topId));
        replyService.addReply(reply);
        return "redirect:topic.do?operate=topicDetail&id="+topId;
    }

    public String delReply(Integer replyId,Integer topicId){
        replyService.delReply(replyId);
        return "redirect:topic.do?operate=topicDetail&id="+topicId;
    }

    public String addHostReply(String content,Integer replyId,HttpSession session){
        Integer topicId=replyService.getTopicId(replyId);
        HostReply hostReply=new HostReply(content,LocalDateTime.now(),
                (UserBasic) session.getAttribute("userBasic"),new Reply(replyId));
        hostReplyService.addHostReply(hostReply);
        return "redirect:topic.do?operate=topicDetail&id="+topicId;
    }
}
