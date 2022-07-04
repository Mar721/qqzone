package com.qqzone.dao;

import com.qqzone.pojo.UserBasic;

import java.util.List;

public interface UserBasicDao {
    //根据账号和密码获取用户信息
    UserBasic getUserBasic(String loginId, String password);
    //根据用户信息获取好友列表
    List<UserBasic> getUserBasicList(UserBasic userBasic);
    //根据id获取用户信息
    UserBasic getUserBasicById(Integer id);

    void addUser(UserBasic userBasic);

    UserBasic getUserByLoginId(String logicId);

    List<UserBasic> getUserListByKey(String keyWord);
}
