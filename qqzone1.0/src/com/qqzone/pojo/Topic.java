package com.qqzone.pojo;


import com.qqzone.dao.UserBasicDao;
import com.qqzone.dao.impl.UserBasicDaoImpl;

import java.time.LocalDateTime;
import java.util.List;

public class Topic {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime topicDate;
    private UserBasic author;      //N:1,一个用户可以有多篇日志
    private List<Reply> replyList;  //1:N，一个日志可以有多个reply

    public Topic(String title, String content, LocalDateTime topicDate, UserBasic author) {
        this.title = title;
        this.content = content;
        this.topicDate = topicDate;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTopicDate() {
        return topicDate;
    }

    public void setTopicDate(LocalDateTime topicDate) {
        this.topicDate = topicDate;
    }

    public UserBasic getAuthor() {
        return author;
    }

    public void setAuthor(UserBasic author) {
        this.author = author;
    }

    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }

    public Topic() {
    }

    public Topic(Integer id) {
        this.id = id;
    }


}
