package com.qqzone.dao.impl;

import com.myssm.basedao.BaseDAO;
import com.qqzone.dao.UserBasicDao;
import com.qqzone.pojo.UserBasic;

import java.util.List;

public class UserBasicDaoImpl extends BaseDAO<UserBasic> implements UserBasicDao {
    @Override
    public UserBasic getUserBasic(String loginId, String password) {
        return super.load("select * from t_user_basic where loginId=? and pwd=?;",loginId,password);
    }

    @Override
    public List<UserBasic> getUserBasicList(UserBasic userBasic) {
            //多表查询
//        return super.executeQuery("select * from t_user_basic t1 " +
//                "left join t_friend t2 on t1.id=t2.uid " +
//                "left join t_user_basic t3 on t2.fid=t3.id;");
        return super.executeQuery("select fid as id from t_friend where uid=?;",userBasic.getId());
    }

    @Override
    public UserBasic getUserBasicById(Integer id) {
        return super.load("select * from t_user_basic where id=?;",id);
    }

    @Override
    public void addUser(UserBasic userBasic) {
        super.executeUpdate("insert into t_user_basic(loginId,nickName,pwd,headImg)values(?,?,?,?);",
                userBasic.getLoginId(),userBasic.getNickName(),userBasic.getPwd(),userBasic.getHeadImg());
    }

    @Override
    public UserBasic getUserByLoginId(String logicId) {
        return super.load("select * from t_user_basic where loginId=?;",logicId);
    }

    @Override
    public List<UserBasic> getUserListByKey(String keyWord) {
        return super.executeQuery("select * from t_user_basic where loginId like ? or nickName like ?;",
                "%"+keyWord+"%","%"+keyWord+"%");
    }
}
