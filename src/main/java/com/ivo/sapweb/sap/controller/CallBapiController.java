package com.ivo.sapweb.sap.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ivo.sapweb.common.JsonResult;
import com.ivo.sapweb.common.util.StringUtil;
import com.ivo.sapweb.common.util.UserAgentGetter;
import com.ivo.sapweb.sap.service.BapiCaller;
import com.sap.conn.jco.JCoException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author wangjian
 * @date 2018/9/19
 */
@Controller
@RequestMapping("/sap/callBapi")
public class CallBapiController {

    @Autowired
    private BapiCaller bapiCaller;


    @ApiOperation(value="访问SAP中的Bapi", notes="传入JSON格式数据")
    @PostMapping("/call")
    @ResponseBody
    public JsonResult call(@RequestBody JSONObject jsonParam, HttpServletRequest request) {

        long startTime = System.currentTimeMillis();

        // 解析请求参数
        String bapiName = jsonParam.getString("bapiName");
        if(StringUtil.isBlank(bapiName)) {
            return JsonResult.error("BAPI的名称不能为空");
        }

        JSONObject ip_json = jsonParam.getJSONObject("inParams");
        Map<String, Object> importParams =
                JSONObject.parseObject(ip_json == null ? null : ip_json.toJSONString(),
                        new TypeReference<Map<String, Object>>(){});

        JSONObject is_json = jsonParam.getJSONObject("inStructures");
        Map<String, Map<String, Object>> inStructures =
                JSONObject.parseObject(is_json == null ? null : is_json.toJSONString(),
                        new TypeReference<Map<String, Map<String, Object>>>(){});

        JSONObject it_json = jsonParam.getJSONObject("inTables");
        Map<String, List<Map<String, Object>>> inTables =
                JSONObject.parseObject(it_json == null ? null : it_json.toJSONString(),
                        new TypeReference<Map<String, List<Map<String, Object>>>>(){});

        JSONArray op_json = jsonParam.getJSONArray("outParamsNames");
        String[] outParamsNames =
                JSONArray.parseObject(op_json == null ? null : op_json.toJSONString(),
                        new TypeReference<String[]>(){});

        JSONArray os_json = jsonParam.getJSONArray("outStructuresNames");
        String[] outStructuresNames =
                JSONArray.parseObject(os_json == null ? null : os_json.toJSONString(),
                        new TypeReference<String[]>(){});

        JSONArray ot_json = jsonParam.getJSONArray("outTablesNames");
        String[] outTablesNames =
                JSONArray.parseObject(ot_json == null ? null : ot_json.toJSONString(),
                        new TypeReference<String[]>(){});

        Boolean parseParamsFlag = jsonParam.getBoolean("parseParamsFlag");
        if (parseParamsFlag == null) {
            parseParamsFlag = false;
        }

        // 使用UserAgentGetter 获取客户端信息
        UserAgentGetter agentGetter = new UserAgentGetter(request);
        String ip = agentGetter.getIpAddr();
        String os = agentGetter.getOS();
        String device = agentGetter.getDevice();
        String browser = agentGetter.getBrowser();

        // 调用bapi
        try {
            Map map = bapiCaller.callBapi(bapiName, importParams, inStructures, inTables, outParamsNames,
                    outStructuresNames, outTablesNames, parseParamsFlag);

            // 计算执行完消耗时间
            long endTime = System.currentTimeMillis();
            String timeConsuming = (endTime - startTime) + "ms";

            // 记录bapi的调用历史
            bapiCaller.recordrBapiUsage(bapiName, true, ip, os, device, browser, timeConsuming);

            return JsonResult.ok().put("data", map);
        } catch (JCoException e) {

            long endTime = System.currentTimeMillis();
            String timeConsuming = (endTime - startTime) + "ms";

            bapiCaller.recordrBapiUsage(bapiName, false, ip, os, device, browser, timeConsuming);
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }

    @PostMapping("/getBapiInfo")
    @ResponseBody
    public JsonResult getBapiInfo(String bapiName) {

        try {
            Map map = bapiCaller.getBapiInfo(bapiName);
            return JsonResult.ok().put("data", map);
        } catch (JCoException e) {
            e.printStackTrace();
            return JsonResult.error(e.getMessage());
        }
    }


}
