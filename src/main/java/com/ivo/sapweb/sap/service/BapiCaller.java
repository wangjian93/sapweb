package com.ivo.sapweb.sap.service;

import com.sap.conn.jco.JCoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjian
 * @date 2018/9/19
 */
public interface BapiCaller {

    /**
     * 获取一个表获取
     * @param bapiName
     * @param inParams
     * @param tableName
     * @return
     * @throws JCoException
     */
    List getObjects(String bapiName, Map inParams, String tableName) throws JCoException;

    /**
     * 获取多个表数据
     * @param bapiName
     * @param inParams
     * @param outTablesNames
     * @return
     * @throws JCoException
     */
    Map getObjects(String bapiName, Map inParams, String[] outTablesNames) throws JCoException;

    /**
     * 保存表数据数据
     * @param bapiName
     * @param inParams
     * @param tableName
     * @param list
     * @param outParamsNames
     * @param outTablesNames
     * @return
     * @throws JCoException
     */
    Map saveObjects(String bapiName, Map inParams, String tableName, List list, String[] outParamsNames,
                           String[] outTablesNames) throws JCoException;

    /**
     * 保存表数据和结构体数据
     * @param bapiName
     * @param inParams
     * @param inStructures
     * @param tableName
     * @param list
     * @param outParamsNames
     * @param outTablesNames
     * @return
     * @throws JCoException
     */
    Map saveObjects(String bapiName, Map inParams, Map inStructures, String tableName, List list, String[] outParamsNames,
                           String[] outTablesNames) throws JCoException;

    /**
     * 保存多个表数据
     * @param bapiName
     * @param inParams
     * @param inStructures
     * @param inTables
     * @param outParamsNames
     * @param outTablesNames
     * @return
     * @throws JCoException
     */
    Map callBapi(String bapiName, Map inParams, Map inStructures, Map inTables, String[] outParamsNames,
                        String[] outTablesNames) throws JCoException;

    /**
     * 完整参数访问Bapi
     * @param bapiName
     * @param inParams
     * @param inStructures
     * @param inTables
     * @param outParamsNames
     * @param outStructuresNames
     * @param outTablesNames
     * @return
     */
    Map callBapi(String bapiName, Map inParams, Map inStructures, Map inTables,String[] outParamsNames,
                        String[] outStructuresNames, String[] outTablesNames) throws JCoException;

    /**
     * 完整参数访问Bapi, 根据parseParamsFlag决定是否对参数进行解析转换java对象
     * @param bapiName
     * @param inParams
     * @param inStructures
     * @param inTables
     * @param outParamsNames
     * @param outStructuresNames
     * @param outTablesNames
     * @param parseParamsFlag
     * @return
     * @throws JCoException
     */
    Map callBapi(String bapiName, Map inParams, Map inStructures, Map inTables,String[] outParamsNames,
                        String[] outStructuresNames, String[] outTablesNames, boolean parseParamsFlag) throws JCoException;

    /**
     * 根据Name从SAP获取bapi信息
     * @param bapiName
     * @return
     * @throws JCoException
     */
    Map getBapiInfo(String bapiName) throws JCoException;

    /**
     * 记录bapi的调用历史
     * @param bapiName
     * @param isSuccess
     * @param ip
     * @param os
     * @param device
     * @param browser
     * @param timeConsuming 
     */
    void recordrBapiUsage(String bapiName, boolean isSuccess, String ip, String os, String device, String browser,
                          String timeConsuming);

    void recordrBapiUsage(String bapiName, boolean isSuccess, String ip, String os, String device, String browser,
                          String timeConsuming, String message);
}
