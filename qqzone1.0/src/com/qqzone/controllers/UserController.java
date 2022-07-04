package com.qqzone.controllers;

import com.myssm.utils.ConnUtils;
import com.qqzone.pojo.Topic;
import com.qqzone.pojo.UserBasic;
import com.qqzone.service.FriendService;
import com.qqzone.service.TopicService;
import com.qqzone.service.UserBasicService;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class UserController {
    private UserBasicService userBasicService;
    private TopicService topicService;
    private FriendService friendService;

    public String login(String loginId, String pwd, HttpSession session){
        UserBasic userBasic = userBasicService.login(loginId, pwd);
        if (userBasic!=null){
            List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
            List<Topic> topicList = topicService.getTopicList(userBasic);
            userBasic.setFriendList(friendList);
            userBasic.setTopicList(topicList);

            //userbasic这个key保存的是登录者信息
            //friend这个key保存的是当前进入的是谁的空间
            session.setAttribute("userBasic",userBasic);
            session.setAttribute("friend",userBasic);

            //后面加好友需要用到
            session.setAttribute("friendList",friendList);

            return "redirect:page.do?operate=page&page=index";
        }
        else {
            return "login";
        }
    }

    public String register(String loginId,String nickName,String pwd,HttpSession session){
        Random random=new Random();
        //已经有这个loginId了
        UserBasic userByLoginId = userBasicService.getUserByLoginId(loginId);
        if (userByLoginId!=null){
            return "register";
        }
        UserBasic userBasic=new UserBasic(loginId,nickName,pwd, "h"+(random.nextInt(6) + 1) +".png");
        userBasicService.addUser(userBasic);

        //这是userBasic还不知道id,所以要根据loginId获取,但这里有一个数据库事务的过滤器，添加不能立刻成功，不能在这里操作
        //但topic那里却没有问题
//        数据库查询没值，但是程序查询有值的情况
//        产生原因：代码bug问题，在全局事物中，前面代码有对数据进行处理，但是没有提交，
//        后面的查询就会从缓存中获取，得到的不是数据库真实的值。这就是数据一致性的问题
//        解决办法：1、减少全局事务，可以使用局部事务处理
//        2、调整代码，避免因为脏数据造成的问题
        //
        try {
            ConnUtils.getConnection().commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        userBasic=userBasicService.getUserByLoginId(loginId);
        //userbasic这个key保存的是登录者信息
        //friend这个key保存的是当前进入的是谁的空间
        session.setAttribute("userBasic",userBasic);
        session.setAttribute("friend",userBasic);
        return "redirect:page.do?operate=page&page=index";
    }


    public String logOut(HttpSession session){
        session.invalidate();
        return "login";
    }

    public String friend(Integer id,HttpSession session){
        UserBasic currFriend = userBasicService.getUserBasicById(id);
        List<Topic> currFriendTopicList = topicService.getTopicList(currFriend);
        currFriend.setTopicList(currFriendTopicList);
        session.setAttribute("friend",currFriend);
        return "index";
    }

    public String findUser(String keyWord,HttpSession session){
        List<UserBasic> userList=userBasicService.getUserListByKey(keyWord);
        //不能加自己为好友，所以移除
        UserBasic userBasic=(UserBasic) session.getAttribute("userBasic");
        userList.remove(userBasic);
        session.setAttribute("findList",userList);
//        friendService.addFriend(userId,userBasic.getId());
        return "redirect:page.do?operate=page&page=find";
    }

    public String addFriend(Integer fid,HttpSession session){
        UserBasic userBasic=(UserBasic) session.getAttribute("userBasic");
        friendService.addFriend(userBasic.getId(),fid);
//        数据库查询没值，但是程序查询有值的情况
//        产生原因：代码bug问题，在全局事物中，前面代码有对数据进行处理，但是没有提交，
//        后面的查询就会从缓存中获取，得到的不是数据库真实的值。这就是数据一致性的问题
//        解决办法：1、减少全局事务，可以使用局部事务处理
//        2、调整代码，避免因为脏数据造成的问题
        //目前只能在这里加一行代码
        try {
            ConnUtils.getConnection().commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //更新friendList
        List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
        userBasic.setFriendList(friendList);
        session.setAttribute("friendList",friendList);
        return "redirect:page.do?operate=page&page=find";
    }
    public String delFriend(Integer fid,HttpSession session){

        UserBasic userBasic=(UserBasic) session.getAttribute("userBasic");
        friendService.delFriend(userBasic.getId(),fid);
        //更新friendList
        List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
        userBasic.setFriendList(friendList);
        session.setAttribute("friendList",friendList);
        return "del";
    }
}
