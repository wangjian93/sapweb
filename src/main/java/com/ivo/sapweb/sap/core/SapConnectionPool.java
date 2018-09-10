package com.ivo.sapweb.sap.core;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Properties;

/**
 * 连接池
 *
 * @author wangjian
 * @date 2018/8/12
 */
public class SapConnectionPool {

    /** 连接属性配置文件名，名称可以随便取 */
    private static final String SAP_CONN="SAP_CONN";


    /**
     * 获取SAP连接
     * @return
     */
    public static JCoDestination getSAPDestination(){
        try {
            JCoDestination dest = JCoDestinationManager.getDestination(SAP_CONN);
            return dest;
        } catch (JCoException ex) {
            System.out.println("重新建立SAP连接池!");
            //重新连接
            return RegetJocodestination();
        }
    }

    /**
     * 重新获取JCODestination
     * @return
     */
    public static JCoDestination RegetJocodestination(){
        try{
            //读取properFile
            Properties properFile = new Properties();
            ClassPathResource classPathResource = new ClassPathResource("SAPConnectionPool.properties");
            InputStream inputStream = classPathResource.getInputStream();
            properFile.load(inputStream);
            inputStream.close();

            //设置jco连接变量信息
            Properties connectProperties = new Properties();
            connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, properFile.getProperty("jco.client.ashost"));
            connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  properFile.getProperty("jco.client.sysnr"));
            connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, properFile.getProperty("jco.client.client"));
            connectProperties.setProperty(DestinationDataProvider.JCO_USER,   properFile.getProperty("jco.client.user"));
            connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, properFile.getProperty("jco.client.passwd"));
            connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   properFile.getProperty("jco.client.lang"));
            // *********连接池方式与直接不同的是设置了下面两个连接属性
            // (1) JCO_PEAK_LIMIT - 同时可创建的最大活动连接数，0表示无限制，默认为JCO_POOL_CAPACITY的值,
            // 如果小于JCO_POOL_CAPACITY的值，则自动设置为该值，在没有设置JCO_POOL_CAPACITY的情况下为0;
            // (2) JCO_POOL_CAPACITY - 空闲连接数，如果为0，则没有连接池效果，默认为1;
            connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, properFile.getProperty("jco.destination.pool_capacity"));
            connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,   properFile.getProperty("jco.destination.peak_limit"));

            //通过DestinationDataProvider, 将连接变量信息保存在内存中
            CustomDestinationDataProvider provider = new CustomDestinationDataProvider();
            provider.addDestinationProperties(SAP_CONN, connectProperties);
            //环境注册provider
            Environment.registerDestinationDataProvider(provider);
            try {
                JCoDestination dest = JCoDestinationManager.getDestination(SAP_CONN);
                return dest;
            } catch (JCoException ex) {
                System.out.println(ex);
                System.out.println("重新连接失败");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
