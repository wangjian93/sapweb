package com.ivo.sapweb.sap.model;

/**
 * sap的function实体类
 *
 * @author wangjian
 * @date 2018/8/16
 */
public class SapFunction {

    /**
     * id
     */
    private Integer functionID;

    /**
     * 名称
     */
    private String functionName;

    /**
     * 描述
     */
    private String description;

    public Integer getFunctionID() {
        return functionID;
    }

    public void setFunctionID(Integer functionID) {
        this.functionID = functionID;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
