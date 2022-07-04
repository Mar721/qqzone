package com.qqzone.dao.impl;

import com.myssm.exception.DAOException;
import com.myssm.utils.ConnUtils;
import com.qqzone.dao.FriendDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FriendDaoImpl implements FriendDao {
    //通用增删改
    public void update(String sql,Object ...args){
        PreparedStatement preparedStatement=null;
        Connection connection = ConnUtils.getConnection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                preparedStatement.setObject(i+1,args[i]);
            }
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new DAOException("通用增删改update出问题了");
        }finally {
            if (preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
    @Override
    public void addFriend(Integer uid, Integer fid) {
        update("insert into t_friend(uid,fid)values(?,?);",uid,fid);
    }

    @Override
    public void delFriend(Integer uid, Integer fid) {
        update("delete from t_friend where uid=? and fid=?;",uid,fid);
    }
}
