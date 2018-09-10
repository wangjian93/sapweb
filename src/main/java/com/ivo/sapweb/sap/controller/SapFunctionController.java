package com.ivo.sapweb.sap.controller;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.ivo.sapweb.common.JsonResult;
import com.ivo.sapweb.common.PageResult;
import com.ivo.sapweb.common.util.JSONUtil;
import com.ivo.sapweb.common.util.StringUtil;
import com.ivo.sapweb.sap.core.BapiCaller;
import com.ivo.sapweb.sap.model.SapFunction;
import com.ivo.sapweb.sap.service.SapFunctionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sap中function管理
 *
 * @author wangjian
 * @date 2018/8/16
 */
@Controller
@RequestMapping("/sap/function")
public class SapFunctionController {

    @Autowired
    private SapFunctionService sapFunctionService;

    @Autowired
    private BapiCaller bapiCaller;

    @RequestMapping()
    public String sapFunctions(Model model) {
        return "sap/executeFunction.html";
    }

    @ResponseBody
    @RequestMapping("/list")
    public PageResult<SapFunction> list(String keyword) {
        List<SapFunction> list = new ArrayList<>();
        SapFunction sapFunction = new SapFunction();
        sapFunction.setFunctionName("ZSD_EIF_VBSN");
        sapFunction.setDescription("ZSD_EIF_VBSN");
        list.add(sapFunction);
        return new PageResult<>(list);
    }

    @ResponseBody
    @RequestMapping("/queryFunctionParams")
    public JsonResult queryFunctionParams(String functionName) {

        Map map = bapiCaller.getFunctionParamsInfo(functionName);

        return JsonResult.ok().put("data", map);
    }

    @RequestMapping("/bapi")
    @ResponseBody
    public JsonResult bapi(@RequestBody JSONObject jsonParam) {

        System.out.println(jsonParam.toJSONString());

        Map<String, Object> map = JSONObject.toJavaObject(jsonParam, Map.class);

        String bapiName = (String) map.get("bapiName");

        Map<String, String> importParams = (Map<String, String>) map.get("importParams");

        Map<String, Map> inStructures = (Map<String, Map>) map.get("inStructures");

        Map<String, List> inTables = (Map<String, List>) map.get("inTables");

        String[]  exportParams = null;
        String exportParam = (String) map.get("exportParams");
        if(!StringUtil.isBlank(exportParam)) {
            exportParams = exportParam.split(",");
        }


        String[] outStructures = null;
        String outStructure = (String) map.get("outStructures");
        if(!StringUtil.isBlank(outStructure)) {
            outStructures = outStructure.split(",");
        }

        String outTable = (String) map.get("outTables");
        String[] outTables = null;
        if(!StringUtil.isBlank(outTable)) {
            outTables = outTable.split(",");
        }


        Map result = bapiCaller.sapCall(bapiName, importParams, inStructures, inTables, exportParams, outStructures, outTables);

        System.out.println(JSONUtils.toJSONString(map));



        return JsonResult.ok().put("data" ,result);
    }
}
