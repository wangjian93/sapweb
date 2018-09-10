package com.ivo.sapweb.sap.core;

import java.util.List;
import java.util.Map;

/**
 * @author wangjian
 * @date 2018/8/15
 */
public class JcoFunctionSuport {

    /**
     * 远程调用sap函数名称
     */
    private String functionName;

    /**
     * import参数
     */
    private Map<String, String> importParams;

    /**
     * import结构体
     */
    private Map<String, Map> inStructures;

    /**
     * 内表--传参
     */
    private Map<String, List> inTables;

    /**
     * export参数
     */
    private Map<String, String> exportParams;

    /**
     * export结构体
     */
    private Map<String, Map> outStructures;

    /**
     * 内表--取参
     */
    private Map<String, List> outTables;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Map<String, String> getImportParams() {
        return importParams;
    }

    public void setImportParams(Map<String, String> importParams) {
        this.importParams = importParams;
    }

    public Map<String, Map> getInStructures() {
        return inStructures;
    }

    public void setInStructures(Map<String, Map> inStructures) {
        this.inStructures = inStructures;
    }

    public Map<String, List> getInTables() {
        return inTables;
    }

    public void setInTables(Map<String, List> inTables) {
        this.inTables = inTables;
    }

    public Map<String, String> getExportParams() {
        return exportParams;
    }

    public void setExportParams(Map<String, String> exportParams) {
        this.exportParams = exportParams;
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
}
