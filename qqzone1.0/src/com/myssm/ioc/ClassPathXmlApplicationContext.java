package com.myssm.ioc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory{
    private Map<String,Object> beanMap=new HashMap<>();

    public ClassPathXmlApplicationContext(){
        this("applicationContext.xml");
    }
    public ClassPathXmlApplicationContext(String path){
        if(path==null){
            throw new RuntimeException("IOC容器配置文件没有指定");
        }
        try {
            InputStream is=getClass().getClassLoader().getResourceAsStream(path);
            //1.创建DocumentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
            //2.创建DocumentBuilder对象
            DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
            //3.创建Document对象
            Document document=documentBuilder.parse(is);

            //4.获取所有的bean节点
            NodeList beannodeList=document.getElementsByTagName("bean");
            for(int i=0;i<beannodeList.getLength();i++){
                Node beanNode=beannodeList.item(i);
                if(beanNode.getNodeType()==Node.ELEMENT_NODE){
                    Element beanElement=(Element) beanNode;
                    String beanId=beanElement.getAttribute("id");
                    String className=beanElement.getAttribute("class");

                    Class beanClass = Class.forName(className);
                    //创建bean实例
                    Object beanObj = beanClass.newInstance();
                    //将bean实例对象保存到map中
                    beanMap.put(beanId,beanObj);
                    //到目前为止，bean与bean之间的依赖关系还没有声明
                }
            }
            //组装bean之间的依赖关系
            for(int i=0;i<beannodeList.getLength();i++) {
                Node beanNode = beannodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    NodeList beanChildNodeList=beanElement.getChildNodes();
                    for(int j=0;j<beanChildNodeList.getLength();j++){
                        Node beanChildNode=beanChildNodeList.item(j);
                        if(beanChildNode.getNodeType()==Node.ELEMENT_NODE&&"property".equals(beanChildNode.getNodeName())){
                            Element propertyElement=(Element) beanChildNode;
                            String propertyName=propertyElement.getAttribute("name");
                            String propertyRef=propertyElement.getAttribute("ref");
                            //找到propertyRef对应的实例
                            Object refObj=beanMap.get(propertyRef);
                            //获取当前bean中对应name的属性
                            Object beanObj=beanMap.get(beanId);
                            Class beanClazz=beanObj.getClass();
                            Field propertyField=beanClazz.getDeclaredField(propertyName);
                            propertyField.setAccessible(true);
                            //将refObj设置到当前bean对于property属性上
                            //beanObj是咧中的属性propertyField的值设置为refObj
                            propertyField.set(beanObj,refObj);
                        }
                    }
                }
            }

        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
