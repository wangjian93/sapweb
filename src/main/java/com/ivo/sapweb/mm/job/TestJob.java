package com.ivo.sapweb.mm.job;

import com.alibaba.druid.support.json.JSONUtils;
import com.ivo.sapweb.sap.core.BapiCaller;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjian
 * @date 2018/9/5
 */
public class TestJob implements Job, Serializable {

    @Autowired
    private BapiCaller bapiCaller;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        String functionName = "ZMM_MSEG_ETL";
        Map<String, String> importParams = new HashMap<>(3);
        importParams.put("I_FROMDATE", "2018-08-15 00:00:00");
        importParams.put("I_TODATE", "2018-09-04 00:00:00");

        String[] outTables = {"T_MSEG"};

        Map map = bapiCaller.getObjects(functionName, importParams, outTables);
        System.out.println(JSONUtils.toJSONString(map));

    }

}
