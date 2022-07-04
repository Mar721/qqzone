package com.qqzone.service;

import com.qqzone.pojo.UserBasic;

import java.util.List;

public interface UserBasicService {
    //登录验证
    UserBasic login(String loginId, String pwd);
    //获取好友列表
    List<UserBasic> getFriendList(UserBasic userBasic);
    //根据id获取用户信息
    UserBasic getUserBasicById(Integer id);

    void addUser(UserBasic userBasic);

    UserBasic getUserByLoginId(String loginId);

    List<UserBasic> getUserListByKey(String keyWord);
}
