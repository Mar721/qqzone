package com.qqzone.service.impl;

import com.qqzone.dao.UserBasicDao;
import com.qqzone.pojo.UserBasic;
import com.qqzone.service.UserBasicService;

import java.util.ArrayList;
import java.util.List;

public class UserBasicServiceImpl implements UserBasicService {
    UserBasicDao userBasicDao = null;
    @Override
    public UserBasic login(String loginId, String pwd) {
        return userBasicDao.getUserBasic(loginId, pwd);
    }

    @Override
    public List<UserBasic> getFriendList(UserBasic userBasic) {
        List<UserBasic> userBasicList = userBasicDao.getUserBasicList(userBasic);
        List<UserBasic> friendList=new ArrayList<>(userBasicList.size());
        for (UserBasic basic : userBasicList) {
            UserBasic userBasicById = userBasicDao.getUserBasicById(basic.getId());
            friendList.add(userBasicById);
        }
        return friendList;
    }

    @Override
    public UserBasic getUserBasicById(Integer id) {
        return userBasicDao.getUserBasicById(id);
    }

    @Override
    public void addUser(UserBasic userBasic) {
        userBasicDao.addUser(userBasic);
    }

    @Override
    public UserBasic getUserByLoginId(String loginId) {
        return userBasicDao.getUserByLoginId(loginId);
    }

    @Override
    public List<UserBasic> getUserListByKey(String keyWord) {
        return userBasicDao.getUserListByKey(keyWord);
    }
}
