package com.ivo.sapweb.sap.controller;

import com.ivo.sapweb.common.PageResult;
import com.ivo.sapweb.sap.model.BapiRecord;
import com.ivo.sapweb.sap.service.BapiRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangjian
 * @date 2018/10/9
 */
@Controller
@RequestMapping("/sap/bapiRecord")
public class BapiRecordController {

    @Autowired
    private BapiRecordService bapiRecordService;

    /**
     * 返回视图
     * @return
     */
    @GetMapping
    public String bapiRecord() {
        return "sap/bapi_record.html";
    }

    /**
     * 查询BapiRecord列表
     */
    @ResponseBody
    @PostMapping("/list")
    public PageResult<BapiRecord> list(Integer page, Integer limit, String searchKey, String searchValue) {
        return bapiRecordService.list(page, limit, searchKey, searchValue);
    }

}
