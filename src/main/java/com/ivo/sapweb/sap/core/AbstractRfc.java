package com.ivo.sapweb.sap.core;

import com.sap.conn.jco.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SAP访问的实现
 * @author wangjian
 * @date 2018/9/15
 */
abstract class AbstractRfc implements RfcCaller {

    @Override
    public void setImportParams(JCoFunction jCoFunction, Map<String, Object> map) {
        if (map == null || map.size() == 0) {
            return;
        }
        // 获取JCoParameterList，传入参数数据
        JCoParameterList jCoParameterList = jCoFunction.getImportParameterList();
        if(jCoParameterList == null) {
            return;
        }
        // 通过JCoFunctionTemplate获取JCoListMetaData
        JCoFunctionTemplate jCoFunctionTemplate = jCoFunction.getFunctionTemplate();
        JCoListMetaData jCoListMetaData = jCoFunctionTemplate.getImportParameterList();

        // 遍历JCoListMetaData，获取参数
        for(int i=0; i<jCoListMetaData.getFieldCount(); i++) {
            // 判断参数类型是否是结构体，是则跳过
            if(jCoListMetaData.isStructure(i)) {
                continue;
            }
            String fieldName = jCoListMetaData.getName(i);
            Object value = map.get(fieldName);
            if(value == null) {
                continue;
            }
            jCoParameterList.setValue(fieldName, value);
        }
    }

    @Override
    public void setImportStructures(JCoFunction jCoFunction, Map<String, Map<String, Object>> map) {
        if (map == null || map.size() == 0) {
            return;
        }
        // 获取JCoParameterList，传入结构体数据
        JCoParameterList jCoParameterList = jCoFunction.getImportParameterList();
        if(jCoParameterList == null) {
            return;
        }
        JCoFunctionTemplate jCoFunctionTemplate = jCoFunction.getFunctionTemplate();
        JCoListMetaData jCoListMetaData = jCoFunctionTemplate.getImportParameterList();

        // 遍历JCoListMetaData，获取结构体
        for(int i=0; i<jCoListMetaData.getFieldCount(); i++) {
            // 判断参数类型是否是结构体，否则跳过
            if(!jCoListMetaData.isStructure(i)) {
                continue;
            }
            String fieldName = jCoListMetaData.getName(i);
            // 获取结构体JCoStructure
            JCoStructure jCoStructure = jCoParameterList.getStructure(fieldName);
            // 获取结构体的JCoRecordMetaData
            JCoRecordMetaData jCoRecordMetaData = jCoStructure.getRecordMetaData();

            Map recordMap = map.get(fieldName);
            if(recordMap == null) {
                continue;
            }

            // 遍历JCoRecordMetaData，获取结构体的参数
            int count = jCoRecordMetaData.getFieldCount();
            for(int j=0; j<count; j++) {
                String recordFieldName = jCoRecordMetaData.getName(j);
                Object value = recordMap.get(recordFieldName);
                if(value == null) {
                    continue;
                }
                jCoStructure.setValue(recordFieldName, value);
            }
        }

    }

    @Override
    public void setImportTables(JCoFunction jCoFunction, Map<String, List<Map<String, Object>>> map) {
        if (map == null || map.size() == 0) {
            return;
        }
        // 获取JCoParameterList, 传入内表数据
        JCoParameterList jCoParameterList = jCoFunction.getTableParameterList();
        if(jCoParameterList == null) {
            return;
        }

        JCoFunctionTemplate jCoFunctionTemplate = jCoFunction.getFunctionTemplate();
        JCoListMetaData jCoListMetaData = jCoFunctionTemplate.getTableParameterList();

        // 遍历JCoListMetaData，获取内表
        for(int i=0; i<jCoListMetaData.getFieldCount(); i++) {
            // 判断内表类型是import还是export，是export型的跳过
            if(!jCoListMetaData.isImport(i)) {
                continue;
            }
            String fieldName = jCoListMetaData.getName(i);
            // 获取内表JCoTable
            JCoTable jCoTable = jCoParameterList.getTable(fieldName);
            // 获取内表的JCoRecordMetaData
            JCoRecordMetaData jCoRecordMetaData = jCoTable.getRecordMetaData();

            List<Map<String, Object>> tableList = map.get(fieldName);
            if(tableList == null) {
                continue;
            }

            // 遍历recordMap，传入内表的每条数据
            for(Map<String, Object> recordMap : tableList) {
                jCoTable.appendRow();

                // 遍历jCoRecordMetaData，获取内表的参数
                for(int j=0; j<jCoRecordMetaData.getFieldCount(); j++) {
                    String recorddName = jCoRecordMetaData.getName(i);
                    Object value = recordMap.get(recorddName);
                    if(value == null) {
                        continue;
                    }
                    jCoTable.setValue(recorddName, value);
                }
            }
        }
    }

    @Override
    public void excute(JCoDestination jCoDestination, JCoFunction jCoFunction) throws JCoException {
        jCoFunction.execute(jCoDestination);
    }

    @Override
    public Map getExportParams(JCoFunction jCoFunction, String[] names) {
        if(names == null || names.length == 0) {
            return null;
        }

        // 获取JCoParameterList, 取出参数数据
        JCoParameterList jCoParameterList = jCoFunction.getExportParameterList();
        if(jCoParameterList == null) {
            return null;
        }

        Map map = new HashMap();

        // 遍历要获取的参数名称
        for(String fieldName : names) {
            map.put(fieldName, jCoParameterList.getValue(fieldName));
        }
        return map;
    }

    @Override
    public Map getExportStructures(JCoFunction jCoFunction, String[] names) {
        if(names == null || names.length == 0) {
            return null;
        }
        // 获取JCoParameterList, 取出结构体数据
        JCoParameterList jCoParameterList = jCoFunction.getExportParameterList();
        if(jCoParameterList == null) {
            return null;
        }

        Map map = new HashMap();

        // 遍历要获取的结构体名称
        for(String fieldName : names) {
            JCoStructure jCoStructure = jCoParameterList.getStructure(fieldName);
            Map structureMap = new HashMap();

            // 遍历结构体的每个栏位
            for(JCoField field : jCoStructure){
                structureMap.put(field.getName(), field.getValue());
            }
            map.put(fieldName, structureMap);
        }
        return map;
    }

    @Override
    public Map getExportTables(JCoFunction jCoFunction, String[] names) {
        if(names == null || names.length == 0) {
            return null;
        }
        // 获取JCoParameterList, 取出内表数据
        JCoParameterList jCoParameterList = jCoFunction.getTableParameterList();
        if(jCoParameterList == null) {
            return null;
        }

        Map map = new HashMap();

        // 遍历获取的内表名称
        for(String fieldName : names) {
            JCoTable jCoTable = jCoParameterList.getTable(fieldName);

            List rowList = new ArrayList();

            // 遍历jCoTable中的每条数据
            for(int i = 0; i < jCoTable.getNumRows(); i++) {
                // 定位到行
                jCoTable.setRow(i);

                Map recordMap = new HashMap();

                // 遍历jCoTable每条数据的每个栏位
                for(JCoField field : jCoTable){
                    recordMap.put(field.getName(), field.getValue());
                }
                rowList.add(recordMap);
            }
            map.put(fieldName, rowList);
        }
        return map;
    }

    @Override
    public Map callRfc(String rfcName, Map<String, Object> inParams, Map<String, Map<String, Object>> inStructures,
                       Map<String, List<Map<String, Object>>> inTables,
                       String[] outParamsNames, String[] outStructuresNames, String[] outTablesNames) throws JCoException {

        // 从连接池获取去连接
        JCoDestination jCoDestination = SapConnectionPool.getSAPDestination();
        // 获取JCoFunction
        JCoFunction jCoFunction = jCoDestination.getRepository().getFunction(rfcName);

        // 传入数据
        setImportParams(jCoFunction, inParams);
        setImportStructures(jCoFunction, inStructures);
        setImportTables(jCoFunction, inTables);
        // 执行
        excute(jCoDestination, jCoFunction);

        // 获取返回数据
        Map paramMap = getExportParams(jCoFunction, outParamsNames);
        Map structsMap = getExportStructures(jCoFunction, outStructuresNames);
        Map tableMap = getExportTables(jCoFunction, outTablesNames);

        Map map = new HashMap();
        if(paramMap != null) {
            map.put("outParams", paramMap);
        }
        if(structsMap != null) {
            map.put("outStructures", structsMap);
        }
        if(tableMap != null) {
            map.put("outTables", tableMap);
        }

        return map;
    }

    @Override
    public Map getRfcParamsInfo(String rfcName) throws JCoException {

        Map map = new HashMap();

        // 获取连接
        JCoDestination jCoDestination = SapConnectionPool.getSAPDestination();
        // 获取JCoFunctionTemplate
        JCoFunctionTemplate function = jCoDestination.getRepository().getFunctionTemplate(rfcName);
        if(function == null) {
            throw new RuntimeException("'" + rfcName + "'" + " function not found in SAP");
        }

        // 输入参数信息
        JCoListMetaData importParams = function.getImportParameterList();
        // 输出参数信息
        JCoListMetaData exportParams = function.getExportParameterList();
        // 内表信息
        JCoListMetaData tablesParams = function.getTableParameterList();

        map.put("import", parseJCoListMetaData(importParams));

        map.put("export", parseJCoListMetaData(exportParams));

        map.put("tables", parseJCoListMetaData(tablesParams));

        return map;
    }

    /**
     * 解析JCoListMetaData获取RFC接口的参数信息
     * @param jCoListMetaData
     * @return
     */
    public List parseJCoListMetaData(JCoListMetaData jCoListMetaData) {
        if(jCoListMetaData == null) {
            return new ArrayList();
        }

        List list = new ArrayList();

        String pname = jCoListMetaData.getName();

        // 遍历jCoListMetaData的field
        for(int i=0; i<jCoListMetaData.getFieldCount(); i++) {
            // 栏位名
            String fieldName = jCoListMetaData.getName(i);
            // 序列
            int indexOf = jCoListMetaData.indexOf(fieldName);
            // 描述
            String description = jCoListMetaData.getDescription(fieldName);
            // 缺省值
            String dafault = jCoListMetaData.getDefault(fieldName);
            // 可选的
            boolean isOptional = jCoListMetaData.isOptional(fieldName);
            // 类型
            String type = jCoListMetaData.getTypeAsString(fieldName);
            // java中对应的Class类型
            String classNameOfField = jCoListMetaData.getClassNameOfField(fieldName);

            boolean isStructure = jCoListMetaData.isTable(fieldName);
            boolean isTable = jCoListMetaData.isTable(fieldName);

            Map map = new HashMap();
            map.put("indexOf", indexOf);
            map.put("fieldName", fieldName);
            map.put("description", description);
            map.put("dafault", dafault);
            map.put("isOptional", isOptional);
            map.put("type", type);
            map.put("classNameOfField", classNameOfField);
            map.put("pname", pname);

            // 判断是否是结构体或内表，是则继续解析JCoRecordMetaData
            if(isStructure || isTable) {
                // Record的栏位信息
                JCoRecordMetaData jCoRecordMetaData = jCoListMetaData.getRecordMetaData(fieldName);
                list.addAll(JCoRecordMetaData(jCoRecordMetaData, fieldName));
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 解析JCoRecordMetaData 获取Record的栏位信息
     * @param jCoRecordMetaData
     * @return
     */
    public List JCoRecordMetaData(JCoRecordMetaData jCoRecordMetaData, String tableName) {
        if(jCoRecordMetaData == null) {
            return new ArrayList();
        }

        List list = new ArrayList();

        String pname = tableName;

        // 遍历jCoRecordMetaData的field
        for(int i=0; i<jCoRecordMetaData.getFieldCount(); i++) {
            // 栏位名
            String fieldName = jCoRecordMetaData.getName(i);
            // 序列
            int indexOf = jCoRecordMetaData.indexOf(fieldName);
            // 描述
            String description = jCoRecordMetaData.getDescription(fieldName);
            // 类型
            String type = jCoRecordMetaData.getTypeAsString(fieldName);
            // java中对应的Class
            String classNameOfField = jCoRecordMetaData.getClassNameOfField(fieldName);

            Map map = new HashMap();
            map.put("indexOf", indexOf);
            map.put("fieldName", fieldName);
            map.put("description", description);
            map.put("type", type);
            map.put("classNameOfField", classNameOfField);
            map.put("pname", pname);
            list.add(map);

        }
        return list;
    }
}
