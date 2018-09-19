package com.ivo.sapweb.sap.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 关联Bapi的表
 * @author wangjian
 * @date 2018/9/17
 */
public class Table {

    /**
     * 表名
     */
    private String name;

    /**
     * 关联的类名
     */
    private String model;

    /**
     * 所有列
     */
    private List columns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List getColumns() {
        return columns;
    }

    public void setColumns(List columns) {
        this.columns = columns;
    }

    public void addColumn(Column column) {
        if (columns == null) columns = new ArrayList();
        columns.add(column);
    }
}
