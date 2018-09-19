package com.ivo.sapweb.sap.core;

import com.sap.conn.jco.JCoException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjian
 * @date 2018/9/15
 */
@Component
public class DefaultRfc extends AbstractRfc {

    /**
     * 将参数数据放入RfcSupport对象中，对RfcSupport为模板进行操作
     * @param rfcName
     * @param inParams
     * @param inStructures
     * @param intables
     * @param outParamsNames
     * @param outStructuresNames
     * @param outTablesNames
     * @param parseParamsFlag 是否需要对参数进行转换
     * @return
     * @throws JCoException
     */
    public Map callRfcTemplete(String rfcName, Map<String, Object> inParams, Map<String, Map<String, Object>> inStructures,
                            Map<String, List> intables,
                            String[] outParamsNames, String[] outStructuresNames, String[] outTablesNames, boolean parseParamsFlag
    ) throws JCoException {
        // 创建RfcSupport
        RfcSupport rfcSupport = new RfcSupport();
        rfcSupport.setRfcName(rfcName);
        rfcSupport.setInParams(inParams);
        rfcSupport.setInStructures(inStructures);
        rfcSupport.setInTables(intables);
        rfcSupport.setOutParamsNames(outParamsNames);
        rfcSupport.setOutStructuresNames(outStructuresNames);
        rfcSupport.setOutTablesNames(outTablesNames);

        // 访问RFC
        callRfc(rfcSupport, parseParamsFlag);

        // 获取返回值
        Map map = new HashMap();
        if (rfcSupport.getOutParams() != null) {
            map.putAll(rfcSupport.getOutParams());
        }
        if (rfcSupport.getOutStructures() != null) {
            map.putAll(rfcSupport.getOutStructures());
        }
        if (rfcSupport.getOutTables() != null) {
            map.putAll(rfcSupport.getOutTables());
        }

        return map;
    }

    /**
     * 使用rfcSupport 访问sap的rfc
     * @param rfcSupport
     * @param parsePramasFlag
     * @throws JCoException
     */
    public void callRfc(RfcSupport rfcSupport, boolean parsePramasFlag) throws JCoException {
        // 判断输入数据是否需要进行转换
        if(parsePramasFlag) {
            parseImport(rfcSupport);
        }

        Map map = super.callRfc(rfcSupport.getRfcName(),
                rfcSupport.getInParams(),
                rfcSupport.getInStructures(),
                (Map<String, List<Map<String, Object>>>) ((Object) rfcSupport.getInTables()),
                rfcSupport.getOutParamsNames(),
                rfcSupport.getOutStructuresNames(),
                rfcSupport.getOutTablesNames());

        rfcSupport.setOutParams((Map<String, Object>) map.get("outParams"));
        rfcSupport.setOutStructures((Map<String, Map>) map.get("outStructures"));
        rfcSupport.setOutTables((Map<String, List>) map.get("outTables"));

        // 判断输出数据是否要进行转换
        if(parsePramasFlag) {
            parseExport(rfcSupport);
        }
    }

    /**
     * 输入数据转换，使输入的参数名与sap中的参数名关联一致
     * @param rfcSupport
     */
    public void parseImport(RfcSupport rfcSupport) {
        Map<String, List> bapiMaps = BapiMapManager.getBapiMap();
        List<Table> tableList = bapiMaps.get(rfcSupport.getRfcName());
        if(tableList == null) {
            return;
        }
        Map<String, Table> tableMap = new HashMap();

        for(Table table : tableList) {
            tableMap.put(table.getName(), table);
        }


        Map<String, List> inTables = rfcSupport.getInTables();
        if(inTables == null) {
            return;
        }
        for(String key : inTables.keySet()) {
            List list = new ArrayList();
            List recordList = inTables.get(key);
            Table table = tableMap.get(key);

            Map<String, Object> map;
            for(Object obj : recordList) {
                Map<String, Object> objMap;
                if(obj instanceof Map) {
                    objMap = (Map<String, Object>) obj;
                } else {
                    objMap = objectToMap(obj);
                }

                Map<String, Object> newMap = new HashMap<>();

                List<Column> columns = table.getColumns();
                for(Column column : columns) {
                    String name = column.getName();
                    String property =  column.getProperty();
                    Object propertyObj = objMap.get(property);
                    newMap.put(name, propertyObj);
                }
                list.add(newMap);
            }
            inTables.put(key, list);
        }
    }


    /**
     * 对输出数据进行转换
     * @param rfcSupport
     */
    public void parseExport(RfcSupport rfcSupport) {
        Map<String, List> bapiMaps = BapiMapManager.getBapiMap();
        List<Table> tableList = bapiMaps.get(rfcSupport.getRfcName());
        if(tableList == null) {
            return;
        }
        Map<String, Table> tableMap = new HashMap();

        for(Table table : tableList) {
            tableMap.put(table.getName(), table);
        }

        Map<String, List> outTables = rfcSupport.getOutTables();

        for(String key : outTables.keySet()) {
            Table table = tableMap.get(key);
            if(table == null) {
                continue;
            }

            List<Map<String, Object>> objList = outTables.get(key);
            List<Object> list = new ArrayList<>();
            for(Map<String, Object> objMap : objList) {
                String model = table.getModel();
                List<Column> columns = table.getColumns();

                Class cls = null;
                try {
                   cls = Class.forName(model);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                for(Column column : columns) {
                    String columnName = column.getName();
                    String columnProperty =  column.getProperty();
                    Object propertyObj = objMap.get(columnName);
                    objMap.remove(columnName);
                    objMap.put(columnProperty, propertyObj);
                }
                Object obj = mapToObject(objMap, cls);
                list.add(obj);
            }

            outTables.put(key, list);
        }
    }

    /**
     * 对象转Map
     * @param obj
     * @return
     */
    public static Map<String, Object> objectToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();

            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * Map转对象
     * @param map
     * @param beanClass
     * @return
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) {
        if (map == null) {
            return null;
        }

        Object obj = null;
        try {
            obj = beanClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }

            field.setAccessible(true);
            try {
                field.set(obj, map.get(field.getName()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return obj;
    }
}
