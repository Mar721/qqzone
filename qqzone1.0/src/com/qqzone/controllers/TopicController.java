package com.qqzone.controllers;

import com.myssm.utils.ConnUtils;
import com.qqzone.pojo.Topic;
import com.qqzone.pojo.UserBasic;
import com.qqzone.service.ReplyService;
import com.qqzone.service.TopicService;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


public class TopicController {
    private TopicService topicService;
    private ReplyService replyService;
    public String topicDetail(Integer id, HttpSession session){
        Topic currTopic = topicService.getTopicById(id);
        session.setAttribute("topicDetail",currTopic);
        return "frames/detail";
    }

    public String delTopic(Integer topicId){
        topicService.delTopicById(topicId);
        //这里也可以跳到userController,但这样不太好
        return "redirect:topic.do?operate=getTopicList";
    }

    public String getTopicList(HttpSession session){
        //从session中获取当前信息
        UserBasic userBasic=(UserBasic) session.getAttribute("userBasic");
        //再次查询当前用户关联的所有日志
        List<Topic> topicList=topicService.getTopicList(userBasic);
        //更新日志列表(因为之前session中的数据和现在数据库中的不一致）
        userBasic.setTopicList(topicList);
        //这里有疑问？
        session.setAttribute("friend",userBasic);
        return "frames/main";
    }

    public String addTopic(String title,String content,HttpSession session){
        UserBasic userBasic = (UserBasic)session.getAttribute("userBasic");
        Topic topic=new Topic(title,content, LocalDateTime.now(),userBasic);
        topicService.addTopic(topic);
        //return "redirect:topic.do?operate=getTopicList";
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
        //再次查询当前用户关联的所有日志
        List<Topic> topicList=topicService.getTopicList(userBasic);
        //更新日志列表(因为之前session中的数据和现在数据库中的不一致）
        userBasic.setTopicList(topicList);
        session.setAttribute("friend",userBasic);
        //不能直接return ”index“,这样刷新的时候会询问你是否重新提交表单
        //因为只是用th渲染把添加页面覆盖了，表单还是存在的
        //所以要重定向
        return "redirect:page.do?operate=page&page=index";
        //return "index";
    }
}
