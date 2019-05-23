package com.ivo.sapweb.sap.core;

import com.ivo.sapweb.SapwebApplication;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory.getLogger(SapwebApplication.class);

    /** 运行环境 **/
    public static String env = "pro";

    /** 连接属性配置文件名，名称可以随便取 */
    private static final String SAP_CONN="SAP_CONN";


    /**
     * 获取SAP连接
     * @return
     */
    public static synchronized JCoDestination getSAPDestination(){
        try {
            JCoDestination dest = JCoDestinationManager.getDestination(SAP_CONN);
            return dest;
        } catch (JCoException ex) {
            logger.error("SAP获取连接失败", ex);
            logger.info("重新建立SAP连接池!");
            //重新连接
            return RegetJocodestination();
        }
    }

    /**
     * 设置运行环境
     * @param env
     */
    public static void setEnv(String env) {
        SapConnectionPool.env = env;
    }

    /**
     * 根据运行环境获取sap连接配置
     */
    public static ClassPathResource getResource() {
        String resourceName = "SAPConnectionPool_" + env + ".properties";
        return new ClassPathResource(resourceName);
    }

    /**
     * 重新获取JCODestination
     * @return
     */
    public static JCoDestination RegetJocodestination(){
        try{

            //读取properFile
            ClassPathResource classPathResource = getResource();
            logger.info("加载SAP连接池配置文件: " + classPathResource.getFilename());

            Properties properFile = new Properties();
            InputStream inputStream = classPathResource.getInputStream();
            properFile.load(inputStream);
            inputStream.close();

            logger.info("建立连接池...");
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

                logger.info("连接池连接信息:");
                logger.info("ApplicationServerHost: " + dest.getApplicationServerHost());
                logger.info("GatewayHost: " + dest.getGatewayHost());
                logger.info("TPHost: " + dest.getTPHost());
                logger.info("MessageServerHost: " + dest.getMessageServerHost());

                return dest;
            } catch (JCoException ex) {
                logger.error("SAP重新连接失败", ex);
            }
            logger.info("SAP连接池创建完成");
        }catch(Exception e){
            logger.error("SAP连接池创建失败:", e);
        }
        return null;
    }

}
