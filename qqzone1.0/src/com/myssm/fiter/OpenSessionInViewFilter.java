package com.myssm.fiter;

import com.myssm.trans.TransactionManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter("*.do")
public class OpenSessionInViewFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            TransactionManager.beginTrans();
            filterChain.doFilter(servletRequest,servletResponse);
            TransactionManager.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                TransactionManager.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void destroy() {

    }
}
