package com.ivo.sapweb.sap.core;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;

import java.util.List;
import java.util.Map;

/**
 * 使用SAPJCO3的操作接口
 * @author wangjian
 * @date 2018/9/15
 */
public interface Rfc {

    /**
     * 传入参数数据
     * @param jCoFunction
     * @param map
     */
    void setImportParams(JCoFunction jCoFunction, Map<String, Object> map);

    /**
     * 传入结构体数据
     * @param jCoFunction
     * @param map
     */
    void setImportStructures(JCoFunction jCoFunction, Map<String, Map<String, Object>> map);

    /**
     * 传入内表数据
     * @param jCoFunction
     * @param map
     */
    void setImportTables(JCoFunction jCoFunction, Map<String, List<Map<String, Object>>> map);

    /**
     * 执行
     * @param jCoDestination
     * @param jCoFunction
     * @throws JCoException
     */
    void excute(JCoDestination jCoDestination, JCoFunction jCoFunction) throws JCoException;

    /**
     * 获取返回的参数
     * @param jCoFunction
     * @param names
     * @return
     */
    Map getExportParams(JCoFunction jCoFunction, String[] names);

    /**
     * 获取返回的结构体
     * @param jCoFunction
     * @param names
     * @return
     */
    Map getExportStructures(JCoFunction jCoFunction, String[] names);

    /**
     * 获取返回的内表
     * @param jCoFunction
     * @param names
     * @return
     */
    Map getExportTables(JCoFunction jCoFunction, String[] names);


}
