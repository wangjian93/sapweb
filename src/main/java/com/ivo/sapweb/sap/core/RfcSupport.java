package com.ivo.sapweb.sap.core;

import java.util.List;
import java.util.Map;

/**
 * RFC操作辅助类, 存放访问SAP需要输入和输出的数据
 * @author wangjian
 * @date 2018/9/15
 */
public class RfcSupport {
    /**
     * 远程调用sap函数名称
     */
    private String rfcName;

    /**
     * import参数
     */
    private Map<String, Object> inParams;

    /**
     * import结构体
     */
    private Map<String, Map<String, Object>> inStructures;

    /**
     * 内表--传参
     */
    private Map<String, List> inTables;

    private String[] outParamsNames;

    private String[] outStructuresNames;

    private String[] outTablesNames;

    /**
     * export参数
     */
    private Map<String, Object> outParams;

    /**
     * export结构体
     */
    private Map<String, Map> outStructures;

    /**
     * 内表--取参
     */
    private Map<String, List> outTables;

    public String getRfcName() {
        return rfcName;
    }

    public void setRfcName(String rfcName) {
        this.rfcName = rfcName;
    }

    public Map<String, Object> getInParams() {
        return inParams;
    }

    public void setInParams(Map<String, Object> inParams) {
        this.inParams = inParams;
    }

    public Map<String, Map<String, Object>> getInStructures() {
        return inStructures;
    }

    public void setInStructures(Map<String, Map<String, Object>> inStructures) {
        this.inStructures = inStructures;
    }

    public Map<String, List> getInTables() {
        return inTables;
    }

    public void setInTables(Map<String, List> inTables) {
        this.inTables = inTables;
    }

    public Map<String, Object> getOutParams() {
        return outParams;
    }

    public void setOutParams(Map<String, Object> outParams) {
        this.outParams = outParams;
    }

    public Map<String, Map> getOutStructures() {
        return outStructures;
    }

    public void setOutStructures(Map<String, Map> outStructures) {
        this.outStructures = outStructures;
    }

    public Map<String, List> getOutTables() {
        return outTables;
    }

    public void setOutTables(Map<String, List> outTables) {
        this.outTables = outTables;
    }

    public String[] getOutParamsNames() {
        return outParamsNames;
    }

    public void setOutParamsNames(String[] outParamsNames) {
        this.outParamsNames = outParamsNames;
    }

    public String[] getOutStructuresNames() {
        return outStructuresNames;
    }

    public void setOutStructuresNames(String[] outStructuresNames) {
        this.outStructuresNames = outStructuresNames;
    }

    public String[] getOutTablesNames() {
        return outTablesNames;
    }

    public void setOutTablesNames(String[] outTablesNames) {
        this.outTablesNames = outTablesNames;
    }
}
