package com.ivo.sapweb.sap.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.ivo.sapweb.sap.core.DefaultRfc;
import com.ivo.sapweb.sap.dao.BapiMapper;
import com.ivo.sapweb.sap.dao.BapiRecordMapper;
import com.ivo.sapweb.sap.model.Bapi;
import com.ivo.sapweb.sap.model.BapiRecord;
import com.ivo.sapweb.sap.service.BapiCaller;
import com.sap.conn.jco.JCoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjian
 * @date 2018/9/19
 */
@Service
public class BapiCallerImpl implements BapiCaller {

    private final static Logger logger = LoggerFactory.getLogger(BapiCallerImpl.class);

    @Autowired
    private DefaultRfc defaultRfc;

    @Autowired
    private BapiMapper bapiMapper;

    @Autowired
    private BapiRecordMapper bapiRecordMapper;

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

    @Override
    public void recordrBapiUsage(String bapiName, boolean isSuccess, String ip, String os, String device, String browser,
                                 String timeConsuming) {
        recordrBapiUsage(bapiName, isSuccess, ip, os, device, browser, timeConsuming, null);
    }

    @Override
    public void recordrBapiUsage(String bapiName, boolean isSuccess, String ip, String os, String device, String browser, String timeConsuming, String message) {
        try {
            Wrapper<Bapi> wrapper = new EntityWrapper<>();
            wrapper.eq("bapi_Name", bapiName);
            wrapper.eq("is_Valid", 1);
            Bapi bapi = bapiMapper.selectList(wrapper).get(0);
            if(bapi == null) {
                throw new RuntimeException("'"+bapiName+"' " + "Bapi 不存在");
            }
            bapi.setCount(bapi.getCount() + 1);
            bapiMapper.updateById(bapi);

            BapiRecord bapiRecord = new BapiRecord();
            bapiRecord.setBapiId(bapi.getBapiId());
            bapiRecord.setSuccess(isSuccess);
            bapiRecord.setIp(ip);
            bapiRecord.setOs(os);
            bapiRecord.setDevice(device);
            bapiRecord.setBrowser(browser);
            bapiRecord.setTimeConsuming(timeConsuming);
            bapiRecord.setCreateTime(new Date());
            bapiRecord.setUpdateTime(new Date());
            bapiRecord.setDate(new Date());
            bapiRecord.setError(message);
            bapiRecordMapper.insert(bapiRecord);
        } catch (Exception e) {
            logger.error("BapiRecord保存失败，" + bapiName, e);
            // 这里只做记录，不干涉业务，由此引发的所有异常捕获不作处理
            return;
        }
    }
}
