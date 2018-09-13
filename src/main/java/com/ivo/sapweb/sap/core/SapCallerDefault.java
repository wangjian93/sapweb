package com.ivo.sapweb.sap.core;

import com.sap.conn.jco.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjian
 * @date 2018/8/15
 */
public class SapCallerDefault implements SapCaller  {

    @Override
    public Map sapCall(String functionName, Map<String, String> importParams, Map<String, Map> inStructures,
                       Map<String, List> inTables, String[] exportParams, String[] outStructures, String[] outTables) {
        //获取开始时间
        long startTime = System.currentTimeMillis();

        JcoFunctionSuport jcoFunctionSuport = createJcoFunctionSuport(functionName, importParams, inStructures, inTables,
                exportParams, outStructures, outTables);

        try {
            excute(jcoFunctionSuport);
        } catch (JCoException e) {
            e.printStackTrace();
        }

        Map map = parseJcoFunctionSuport(jcoFunctionSuport);

        long endTime = System.currentTimeMillis();
        System.out.println("访问SAP接口 " + jcoFunctionSuport.getFunctionName() + " 耗时：" +(endTime-startTime) + "ms");

        return map;
    }

    @Override
    public Map getFunctionParamsInfo(String functionName) {

        Map map = new HashMap();

        try {
            JCoDestination jCoDestination = SapConnectionPool.getSAPDestination();
            JCoFunctionTemplate function = jCoDestination.getRepository().getFunctionTemplate(functionName);
            if(function == null) {
                throw new RuntimeException(functionName + " function not found in SAP");
            }

            JCoListMetaData importParams = function.getImportParameterList();
            JCoListMetaData exportParams = function.getExportParameterList();
            JCoListMetaData tablesParams = function.getTableParameterList();

            if(importParams != null) {
                map.put("import", parseJCoListMetaData(importParams));
            }
            if(exportParams != null) {
                map.put("export", parseJCoListMetaData(exportParams));
            }
            if(tablesParams != null) {
                map.put("tables", parseJCoListMetaData(tablesParams));
            }
        } catch(JCoException e) {
            e.printStackTrace();
        }

        JCoTable jCoTable;


        return map;

    }

    /**
     * 解析JcoFunctionSuport执行完后的结果
     * @return
     */
    public Map parseJcoFunctionSuport(JcoFunctionSuport jcoFunctionSuport) {
        Map<String, Object> map = new HashMap();
        Map<String, String> exportParams = jcoFunctionSuport.getExportParams();
        Map<String, Map> outStructures = jcoFunctionSuport.getOutStructures();
        Map<String, List> outTables = jcoFunctionSuport.getOutTables();

        map.putAll(exportParams);
        map.putAll(outStructures);
        map.putAll(outTables);

        return map;
    }

    /**
     * 将import和export 数据封装到对象中, 转化为对JcoFunctionSuport的操作
     * @param functionName
     * @param importParams
     * @param inStructures
     * @param inTables
     * @param exportParams
     * @param outStructures
     * @param outTables
     * @return
     */
    public JcoFunctionSuport createJcoFunctionSuport(String functionName, Map<String, String> importParams,
                                                     Map<String, Map> inStructures, Map<String, List> inTables,
                                                     String[] exportParams, String[] outStructures, String[] outTables) {

        JcoFunctionSuport jcoFunctionSuport = new JcoFunctionSuport();

        //设置import参数、结构体、内表
        jcoFunctionSuport.setFunctionName(functionName);
        jcoFunctionSuport.setImportParams(importParams);
        jcoFunctionSuport.setInStructures(inStructures);
        jcoFunctionSuport.setInTables(inTables);

        //设置export参数名、结构体名、内表名（值全部设为null)
        //在function执行完后会将结果存入
        Map exportParamsMap = new HashMap();
        if(exportParams != null) {
            for(String param : exportParams) {
                exportParamsMap.put(param, null);
            }
        }
        jcoFunctionSuport.setExportParams(exportParamsMap);

        Map outStructuresMap = new HashMap();
        if(outStructures != null) {
            for(String param : outStructures) {
                outStructuresMap.put(param, null);
            }
        }
        jcoFunctionSuport.setOutStructures(outStructuresMap);

        Map outTablesMap =new HashMap();
        if(outTables != null) {
            for(String param : outTables) {
                outTablesMap.put(param, null);
            }
        }
        jcoFunctionSuport.setOutTables(outTablesMap);

        return jcoFunctionSuport;
    }

    /**
     * function执行
     * @param jcoFunctionSuport
     * @throws JCoException
     */
    public void excute(JcoFunctionSuport jcoFunctionSuport) throws JCoException {

        //获取连接
        JCoDestination jCoDestination = SapConnectionPool.getSAPDestination();

        //获取JCoFunction
        JCoFunction function = jCoDestination.getRepository().getFunction(jcoFunctionSuport.getFunctionName());
        if(function == null) {
            throw new RuntimeException(jcoFunctionSuport.getFunctionName() + " function not found in SAP.");
        }

        //设置import参数
        initImportParams(function, jcoFunctionSuport.getImportParams());

        //设置import结构体
        initInStructures(function, jcoFunctionSuport.getInStructures());

        //设置import内表
        initInTables(function, jcoFunctionSuport.getInTables());

        JCoContext.begin(jCoDestination);

        //执行
        function.execute(jCoDestination);

        JCoContext.end(jCoDestination);

        //取export参数
        initExportParams(function, jcoFunctionSuport.getExportParams());

        //取export结构体
        initOutStructures(function, jcoFunctionSuport.getOutStructures());

        //取export内表
        initOutTables(function, jcoFunctionSuport.getOutTables());
    }

    /**
     * import参数设置
     * @param function
     * @param map
     */
    public void initImportParams(JCoFunction function, Map<String, String> map) {
        if(map == null || map.size()<1) {
            return;
        }
        JCoParameterList importParam = function.getImportParameterList();
        if(importParam == null) {
            throw new RuntimeException(function.getName() + " of import parameter not found");
        }
        for (String key : map.keySet()) {
            importParam.setValue(key, map.get(key));
        }
    }

    /**
     * import结构体设置
     * @param function
     * @param map
     */
    public void initInStructures(JCoFunction function, Map<String, Map> map) {
        if(map == null || map.size()<1) {
            return;
        }
        JCoParameterList importParam = function.getImportParameterList();
        if(importParam == null) {
            throw new RuntimeException(function.getName() + " of import parameter not found");
        }
        for (String key : map.keySet()) {
            JCoStructure structure = importParam.getStructure(key);
            if(structure == null) {
                throw new RuntimeException(key + " that structure type parameter not found");
            }
            Map<String, String> stMap = map.get(key);
            for(String k : stMap.keySet()) {
                structure.setValue(k, stMap.get(k));
            }
        }
    }

    /**
     * import内表设置
     * @param function
     * @param map
     */
    public void initInTables(JCoFunction function, Map<String, List> map) {
        if(map == null || map.size()<1) {
            return;
        }
        JCoParameterList importParam = function.getTableParameterList();
        if(importParam == null) {
            throw new RuntimeException(function.getName() + " of import type parameter not found");
        }
        for(String key : map.keySet()) {
            JCoTable inTable = importParam.getTable(key);
            if(inTable == null) {
                throw new RuntimeException(key + " that table type paramter not found");
            }
            List<Map> list = map.get(key);
            for(Map<String, String> row : list) {
                inTable.appendRow();
                for(String k : row.keySet()) {
                    inTable.setValue(k, row.get(k));
                }
            }
        }
    }

    /**
     * 取export参数
     * @param function
     * @param map
     */
    public void initExportParams(JCoFunction function, Map<String, String> map) {
        if(map == null || map.size()<1) {
            return;
        }
        JCoParameterList exportParam = function.getExportParameterList();
        if(exportParam == null) {
            throw new RuntimeException(function.getName() + " of export type parameter not found");
        }
        for(String key : map.keySet()) {
            map.put(key, exportParam.getString(key));
        }
    }

    /**
     * 取export结构体
     * @param function
     * @param map
     */
    public void initOutStructures(JCoFunction function, Map<String, Map> map) {
        if(map == null || map.size()<1) {
            return;
        }
        JCoParameterList exportParam = function.getExportParameterList();
        if(exportParam == null) {
            throw new RuntimeException(function.getName() + " of export type parameter not found");
        }
        for(String key : map.keySet()) {
            JCoStructure structure = exportParam.getStructure(key);
            if(structure == null) {
                throw new RuntimeException(key + " that structure type parameter not found");
            }
            Map structureMap = new HashMap();
            for(JCoField field : structure){
                structureMap.put(field.getName(), field.getString());
            }
            map.put(key, structureMap);
        }
    }

    /**
     * 取export内表
     * @param function
     * @param map
     */
    public void initOutTables(JCoFunction function, Map<String, List> map) {
        if(map == null || map.size()<1) {
            return;
        }
        JCoParameterList exportParam = function.getTableParameterList();
        if(exportParam == null) {
            throw new RuntimeException(function.getName() + " of table type parameter not found");
        }
        for(String key : map.keySet()) {
            JCoTable outTable = exportParam.getTable(key);
            if(outTable == null) {
                throw new RuntimeException(key + " that table type parameter not found");
            }
            //表中数据总条数
            int numRows = outTable.getNumRows();
            List list = new ArrayList();
            if(numRows>1000) {
                Map row = new HashMap();
                row.put("msg", "数据量过大,不能读取( "+numRows+" 条）");
                list.add(row);
            } else {
                for(int i = 0; i < outTable.getNumRows(); i++) {
                    outTable.setRow(i);
                    Map row = new HashMap();
                    for(JCoField field : outTable){
                        row.put(field.getName(), field.getString());
                    }
                    list.add(row);
                }
            }
            map.put(key, list);
        }
    }

    public List parseJCoListMetaData(JCoListMetaData jCoListMetaData) {
        if(jCoListMetaData == null) {
            return null;
        }

        List list = new ArrayList();

        for(int i=0; i<jCoListMetaData.getFieldCount(); i++) {
            String fieldName = jCoListMetaData.getName(i);
            int indexOf = jCoListMetaData.indexOf(fieldName);
            // 描述
            String description = jCoListMetaData.getDescription(fieldName);
            // 缺省值
            String dafault = jCoListMetaData.getDefault(fieldName);
            // 可选的
            boolean isOptional = jCoListMetaData.isOptional(fieldName);
            // 类型
            String type = jCoListMetaData.getTypeAsString(fieldName);
            // Class类型
            String classNameOfField = jCoListMetaData.getClassNameOfField(fieldName);

            boolean isStructure = jCoListMetaData.isTable(fieldName);
            boolean isTable = jCoListMetaData.isTable(fieldName);

            Map map = new HashMap();
            map.put("indexOf", indexOf);
            map.put("fielName", fieldName);
            map.put("description", description);
            map.put("dafault", dafault);
            map.put("isOptional", isOptional);
            map.put("type", type);
            map.put("classNameOfField", classNameOfField);
            if(isStructure || isTable) {
                JCoRecordMetaData jCoRecordMetaData = jCoListMetaData.getRecordMetaData(fieldName);
                map.put("records", JCoRecordMetaData(jCoRecordMetaData));
            }
            list.add(map);
        }
        return list;
    }

    public List JCoRecordMetaData(JCoRecordMetaData jCoRecordMetaData) {
        if(jCoRecordMetaData == null) {
            return null;
        }

        List list = new ArrayList();
        for(int i=0; i<jCoRecordMetaData.getFieldCount(); i++) {
            String fieldName = jCoRecordMetaData.getName(i);
            int indexOf = jCoRecordMetaData.indexOf(fieldName);
            String description = jCoRecordMetaData.getDescription(fieldName);
            String type = jCoRecordMetaData.getTypeAsString(fieldName);
            String classNameOfField = jCoRecordMetaData.getTypeAsString(fieldName);

            Map map = new HashMap();
            map.put("indexOf", indexOf);
            map.put("fieldName", fieldName);
            map.put("description", description);
            map.put("type", type);
            map.put("classNameOfField", classNameOfField);
            list.add(map);
        }
        return list;
    }

    public List parseMetaData(JCoListMetaData jCoListMetaData) {
        List list = new ArrayList();
        int fielCount = jCoListMetaData.getFieldCount();
        for(int i=0; i<fielCount; i++) {
            Map map = new HashMap();

            String fielName = jCoListMetaData.getName(i);
            map.put("fielName", fielName);

            int indexOf = jCoListMetaData.indexOf(fielName);
            map.put("indexOf", indexOf);

            String dafault = jCoListMetaData.getDefault(fielName);
            map.put("dafault", dafault);

            int length = jCoListMetaData.getByteLength(fielName);
            map.put("length", length);

            String type = jCoListMetaData.getTypeAsString(fielName);
            map.put("type", type);

            boolean isStructure = jCoListMetaData.isStructure(fielName);
            map.put("isStructure", isStructure);

            boolean  isOptional = jCoListMetaData.isOptional(fielName);
            map.put("isOptional", isOptional);

            String description = jCoListMetaData.getDescription(fielName);
            map.put("description", description);

            int decimals = jCoListMetaData.getDecimals(fielName);
            map.put("decimals", decimals);

            String classNameOfField = jCoListMetaData.getClassNameOfField(fielName);
            map.put("classNameOfField", classNameOfField);

            String recordFieldName = jCoListMetaData.getRecordFieldName(fielName);
            map.put("recordFieldName", recordFieldName);

            String recordTypeName = jCoListMetaData.getRecordTypeName(fielName);
            map.put("recordTypeName", recordTypeName);

            JCoRecordMetaData jCoRecordMetaData = jCoListMetaData.getRecordMetaData(fielName);
            if(jCoRecordMetaData != null) {
                List list1 = new ArrayList();

                int count = jCoRecordMetaData.getFieldCount();
                for(int j=0; j<count; j++) {
                    Map map1 = new HashMap();
                    String fieldName = jCoRecordMetaData.getName(j);
                    int fieldLength = jCoRecordMetaData.getLength(j);
                    String fieldDescription = jCoRecordMetaData.getDescription(j);
                    int fieldDecimals = jCoRecordMetaData.getDecimals(j);
                    String fieldType = jCoRecordMetaData.getTypeAsString(j);
                    String fieldClassNameofField = jCoRecordMetaData.getClassNameOfField(i);
                    map1.put("fieldName", fieldName);
                    map1.put("length", fieldLength);
                    map1.put("description", fieldDescription);
                    map1.put("decimals", fieldDecimals);
                    map1.put("type", fieldType);
                    map1.put("classNameOfField", fieldClassNameofField);
                    list1.add(map1);
                }
                map.put("recordMetaData", list1);
            }

            list.add(map);
        }
        return list;
    }
}
