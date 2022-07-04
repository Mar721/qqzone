package com.qqzone.pojo;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class UserBasic {
    private Integer id;
    private String loginId;
    private String nickName;
    private String pwd;
    private String headImg;

    private UserDetail userDetail;  //1:1,一个用户对应一详细信息
    private List<Topic> topicList;  //1:N,一个用户可以有多个日志


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBasic userBasic = (UserBasic) o;
        return id.equals(userBasic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private List<UserBasic> friendList;  //M:N,每个用户都可以有多个好友

    public UserBasic(String loginId, String nickName, String pwd, String headImg) {
        this.loginId = loginId;
        this.nickName = nickName;
        this.pwd = pwd;
        this.headImg = headImg;
    }

    public UserBasic(Integer id) {
        this.id = id;
    }

    public UserBasic() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public List<UserBasic> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<UserBasic> friendList) {
        this.friendList = friendList;
    }
}
