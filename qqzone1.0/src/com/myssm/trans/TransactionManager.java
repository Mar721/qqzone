package com.myssm.trans;

import com.myssm.utils.ConnUtils;

import java.sql.SQLException;

public class TransactionManager {

    public static void beginTrans() throws SQLException {
        ConnUtils.getConnection().setAutoCommit(false);
    }

    public static void commit() throws SQLException {
        ConnUtils.getConnection().commit();
        ConnUtils.closeConn();
    }

    public static void rollback() throws SQLException {
        ConnUtils.getConnection().rollback();
        ConnUtils.closeConn();
    }
}
