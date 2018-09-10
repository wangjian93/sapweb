package com.ivo.sapweb.sap.core;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * DestinationDataProvider接口实现,将连接变量信息存放在内存里
 *
 * @author wangjian
 * @date 2018/8/12
 */
public class CustomDestinationDataProvider implements DestinationDataProvider {

    private Map providers = new HashMap();

    /**
     * 获取连接配置属性
     * @param destName
     * @return
     */
    @Override
    public Properties getDestinationProperties(String destName) {
        if (destName == null) {
            throw new NullPointerException("请指定目的名称");
        }
        if (providers.size() == 0) {
            throw new IllegalStateException("请加入一个目的连接参数属性给提供者");
        }
        return (Properties)providers.get(destName);
    }

    /**
     * 没有实现事件处理
     * @return
     */
    @Override
    public boolean supportsEvents(){
        return false;
    }

    @Override
    public void setDestinationDataEventListener(DestinationDataEventListener listener) {
        throw new UnsupportedOperationException();
    }

    /**
     * 添加连接配置属性
     * @param destName
     * @param provider
     */
    public void addDestinationProperties(String destName, Properties provider) {
        providers.put(destName, provider);
    }
}
