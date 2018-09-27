package com.ivo.sapweb.system.controller;

import com.ivo.sapweb.common.PageResult;
import com.ivo.sapweb.common.util.StringUtil;
import com.ivo.sapweb.system.model.LoginRecord;
import com.ivo.sapweb.system.service.LoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录日志管理
 * @author wangjian
 * @date 2018/9/3
 */
@Controller
@RequestMapping("/system/loginRecord")
public class LoginRecordController {

    @Autowired
    private LoginRecordService loginRecordService;

    @GetMapping()
    public String loginRecord() {
        return "system/login_record.html";
    }

    /**
     * 查询所有登录日志
     **/
    @ResponseBody
    @PostMapping("/list")
    public PageResult<LoginRecord> list(Integer page, Integer limit, String startDate, String endDate, String account) {
        if (StringUtil.isBlank(startDate)) {
            startDate = null;
        } else {
            startDate += " 00:00:00";
        }
        if (StringUtil.isBlank(endDate)) {
            endDate = null;
        } else {
            endDate += " 23:59:59";
        }
        if (StringUtil.isBlank(account)) {
            account = null;
        }
        return loginRecordService.list(page, limit, startDate, endDate, account);
    }
}
