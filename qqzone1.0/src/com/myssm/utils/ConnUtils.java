package com.myssm.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnUtils {
    private static ThreadLocal<Connection> threadLocal=new ThreadLocal<>();
    private static DruidDataSource dataSource;

    public static void initDataSource(){
        Properties properties=new Properties();
        String file = Thread.currentThread().getContextClassLoader().getResource("jdbc.properties").getFile();
        InputStream is= null;
        try {
            is = new FileInputStream(file);
            properties.load(is);
            dataSource = (DruidDataSource)DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void destoryDataSource(){
        dataSource.close();
    }

    public static Connection createConnection(){
        Connection conn;
        try {
            conn=dataSource.getConnection();
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection(){
        Connection connection=threadLocal.get();
        if(connection==null){
            connection= createConnection();
            threadLocal.set(connection);
        }
        return threadLocal.get();
    }

    public static void closeConn() throws SQLException {
        Connection connection=threadLocal.get();
        if(connection==null){
            return ;
        }
        if(!connection.isClosed()){
            connection.close();
            threadLocal.set(null);
        }
    }
}
