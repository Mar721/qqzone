package com.qqzone.service.impl;


import com.qqzone.dao.FriendDao;
import com.qqzone.service.FriendService;

public class FriendServiceImpl implements FriendService {
    private FriendDao friendDao;
    @Override
    public void addFriend(Integer uid, Integer fid) {
        friendDao.addFriend(uid,fid);
    }

    @Override
    public void delFriend(Integer uid, Integer fid) {
        friendDao.delFriend(uid, fid);
    }
}
