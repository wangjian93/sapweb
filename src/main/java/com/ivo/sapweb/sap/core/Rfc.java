package com.ivo.sapweb.sap.core;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;

import java.util.List;
import java.util.Map;

/**
 * SAPJCO3接口的操作
 * @author wangjian
 * @date 2018/9/15
 */
public interface Rfc {
    /**
     * 传入参数数据
     */
    void setImportParams(JCoFunction jCoFunction, Map<String, Object> map);


    /**
     * 传入结构体数据
     */
    void setImportStructures(JCoFunction jCoFunction, Map<String, Map<String, Object>> map);

    /**
     * 传入内表数据
     */
    void setImportTables(JCoFunction jCoFunction, Map<String, List<Map<String, Object>>> map);

    /**
     * 执行
     */
    void excute(JCoDestination jCoDestination, JCoFunction jCoFunction) throws JCoException;

    /**
     * 获取返回的参数
     */
    Map getExportParams(JCoFunction jCoFunction, String[] names);

    /**
     * 获取返回的结构体
     */
    Map getExportStructures(JCoFunction jCoFunction, String[] names);

    /**
     * 获取返回的内表
     */
    Map getExportTables(JCoFunction jCoFunction, String[] names);


}
