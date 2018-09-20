package com.ivo.sapweb.sap.core;

import java.util.Map;

/**
 * 管理bapi中table与java对象的映射
 * @author wangjian
 * @date 2018/9/19
 */
public class BapiMapManager {

    /**
     * bapiMap xml 配置文件
     */
    private static final String mapConfigFile = "bapi-map-config.xml";

    public static Map bapiMap;

    public static Map getBapiMap() {
        if(bapiMap == null) {
            initBapiMap();
        }
        return bapiMap;
    }

    public static void setBapiMap(Map bapiMap) {
        BapiMapManager.bapiMap = bapiMap;
    }

    public static Map initBapiMap() {
        System.out.println("读取bapi管理的xml，解析获取bapiMap");
        return bapiMap = BapiMapXmlParse.getBapiMap(mapConfigFile);
    }
}
