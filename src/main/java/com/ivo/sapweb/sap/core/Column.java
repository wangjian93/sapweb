package com.ivo.sapweb.sap.core;

/**
 * 关联Bapi中表的列
 * @author wangjian
 * @date 2018/9/17
 */
public class Column {

    /**
     * 列名
     */
    private String name;

    /**
     * java中属性名
     */
    private String property;

    /**
     * java中类型
     */
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
