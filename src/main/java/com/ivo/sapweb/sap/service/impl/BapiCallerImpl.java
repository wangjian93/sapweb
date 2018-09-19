package com.ivo.sapweb.sap.service.impl;

import com.ivo.sapweb.sap.core.DefaultRfc;
import com.ivo.sapweb.sap.service.BapiCaller;
import com.sap.conn.jco.JCoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjian
 * @date 2018/9/19
 */
@Service
public class BapiCallerImpl implements BapiCaller {

    @Autowired
    private DefaultRfc defaultRfc;

    @Override
    public List getObjects(String bapiName, Map inParams, String tableName) throws JCoException {

        String[] outTablesNames = {tableName};
        Map map = getObjects(bapiName, inParams, outTablesNames);
        List list = (List) map.get(tableName);
        return list;
    }

    @Override
    public Map getObjects(String bapiName, Map inParams, String[] outTablesNames) throws JCoException {

        Map map = callBapi(bapiName, inParams, null, null ,null,
                null, outTablesNames);
        return map;
    }

    @Override
    public Map saveObjects(String bapiName, Map inParams, String tableName, List list, String[] outParamsNames,
                           String[] outTablesNames) throws JCoException {

        Map inTables = new HashMap();
        inTables.put(tableName, list);

        Map map = callBapi(bapiName, inParams, null, inTables, outParamsNames, null,
                outTablesNames);

        return map;
    }

    @Override
    public Map saveObjects(String bapiName, Map inParams, Map inStructures, String tableName, List list,
                           String[] outParamsNames, String[] outTablesNames) throws JCoException {

        Map inTables = new HashMap();
        inTables.put(tableName, list);

        Map map = callBapi(bapiName, inParams, inStructures, inTables, outParamsNames, null, outTablesNames);

        return map;
    }

    @Override
    public Map callBapi(String bapiName, Map inParams, Map inStructures, Map inTables, String[] outParamsNames,
                        String[] outTablesNames) throws JCoException {

        Map map = callBapi(bapiName, inParams, inStructures, inTables, outParamsNames, null, outTablesNames);
        return map;
    }

    @Override
    public Map callBapi(String bapiName, Map inParams, Map inStructures, Map inTables,String[] outParamsNames,
                        String[] outStructuresNames, String[] outTablesNames) throws JCoException {

        Map map = callBapi(bapiName, inParams, inStructures, inTables, outParamsNames, outStructuresNames,
                outTablesNames, true );
        return map;
    }

    @Override
    public Map callBapi(String bapiName, Map inParams, Map inStructures, Map inTables,String[] outParamsNames,
                        String[] outStructuresNames, String[] outTablesNames, boolean parseParamsFlag) throws JCoException {

        Map map = defaultRfc.callRfcTemplete(bapiName, inParams, inStructures, inTables, outParamsNames,
                outStructuresNames, outTablesNames, parseParamsFlag);
        return map;
    }

    @Override
    public Map getBapiInfo(String bapiName) throws JCoException {
        return defaultRfc.getRfcParamsInfo(bapiName);
    }
}
