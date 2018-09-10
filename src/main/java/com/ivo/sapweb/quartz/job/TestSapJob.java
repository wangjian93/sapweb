package com.ivo.sapweb.quartz.job;

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
 * @date 2018/9/3
 */
public class TestSapJob implements Job, Serializable {

    @Autowired
    private BapiCaller bapiCaller;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        String functionName = "ZSD_EIF_VBSN";
        Map<String, String> importParams = new HashMap<>(3);
        importParams.put("VBELN", "0080010000");
        importParams.put("POSNR", "10");
        importParams.put("AESKD", "M10-Z");

        String[] exportParams = {"L_SUBRC"};

        Map map = bapiCaller.sapCall(functionName, importParams, null, null,
                exportParams, null, null);

        System.out.println(JSONUtils.toJSONString(map));
    }
}
