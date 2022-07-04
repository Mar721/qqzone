package com.myssm.myspringmvc;
import com.myssm.exception.DispatcherServletException;
import com.myssm.ioc.BeanFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet{

    private BeanFactory beanFactory;
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext=getServletContext();
        Object beanFactoryObj = servletContext.getAttribute("beanFactory");
        if(beanFactoryObj!=null){
            beanFactory=(BeanFactory)beanFactoryObj;
        }else {
            throw new RuntimeException("IOC容器获取失败");
        }
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        servletPath=servletPath.substring(1,servletPath.length()-3);

        Object controllerbeanObj = beanFactory.getBean(servletPath);

        String operate = req.getParameter("operate");
        if(operate==null){
            operate="page";
        }

        try {
            Method[] methods=controllerbeanObj.getClass().getDeclaredMethods();
            for (Method method:methods){
                if (operate.equals(method.getName())){
                    //1.统一请求参数
                    Parameter[] parameters=method.getParameters();
                    //parameterValues用来承载参数的值
                    Object[] parameterValues=new Object[parameters.length];
                    for (int i=0;i< parameters.length;i++){
                        Parameter parameter=parameters[i];
                        String parameterName = parameter.getName();
                        if ("req".equals(parameterName)){
                            parameterValues[i]=req;
                        }else if("resp".equals(parameterName)){
                            parameterValues[i]=resp;
                        }else if ("session".equals(parameterName)){
                            parameterValues[i]=req.getSession();
                        }else {
                            //req统一获取参数
                            String parameterValue=req.getParameter(parameterName);
                            String typename=parameter.getType().getName();
                            Object parameterObj=parameterValue;
                            if(parameterObj!=null){
                                if("java.lang.Integer".equals(typename)){
                                    parameterObj=Integer.parseInt(parameterValue);
                                }else if("java.lang.Double".equals(typename)){
                                    parameterObj=Double.parseDouble(parameterValue);
                                }
                            }
                            parameterValues[i]=parameterObj;
                        }
                    }
                    method.setAccessible(true);
                    Object returnObj=method.invoke(controllerbeanObj,parameterValues);
                    String returnStr=(String) returnObj;
                    if(returnStr==null){
                        return;
                    }
                    //重定向
                    if (returnStr.startsWith("redirect:")){
                        String redirectStr=returnStr.substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    }else if (returnStr.startsWith("json:")){
                        String jsonStr=returnStr.substring("json:".length());
                        PrintWriter out=resp.getWriter();
                        out.write(jsonStr);
                    } else {
                        super.processTemplate(returnStr,req,resp);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DispatcherServletException("DispatcherServlet出问题了");
        }

    }
}
