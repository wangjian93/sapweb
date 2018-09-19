package com.ivo.sapweb.sap.core;

import com.sap.conn.jco.JCoException;

import java.util.List;
import java.util.Map;

/**
 * 访问SAP中RFC
 * @author wangjian
 * @date 2018/9/15
 */
public interface RfcCaller extends Rfc {

    /**
     * 调用sap中的RFC
     * @param rfcName
     * @param inParams
     * @param inStructures
     * @param inTables
     * @param outParamsNames
     * @param outStructuresNames
     * @param outTablesNames
     * @return
     * @throws JCoException
     */
    Map callRfc(String rfcName, Map<String, Object> inParams, Map<String, Map<String, Object>> inStructures,
                Map<String, List<Map<String, Object>>> inTables, String[] outParamsNames, String[] outStructuresNames,
                String[] outTablesNames) throws JCoException;

    /**
     * 获取RFC的各参数信息
     * @param rfcName
     * @return
     * @throws JCoException
     */
    Map getRfcParamsInfo(String rfcName) throws JCoException;
}
