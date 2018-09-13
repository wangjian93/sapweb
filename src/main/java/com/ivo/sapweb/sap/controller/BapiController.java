package com.ivo.sapweb.sap.controller;

import com.ivo.sapweb.common.BaseController;
import com.ivo.sapweb.common.JsonResult;
import com.ivo.sapweb.common.PageResult;
import com.ivo.sapweb.sap.core.BapiCaller;
import com.ivo.sapweb.sap.model.Bapi;
import com.ivo.sapweb.sap.service.BapiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * @author wangjian
 * @date 2018/9/10
 */
@Controller
@RequestMapping("/sap/bapi")
public class BapiController extends BaseController {

    @Autowired
    private BapiService bapiService;

    @Autowired
    private BapiCaller bapiCaller;

    @RequestMapping
    public String bapi() {
        return "sap/bapi.html";
    }

    @RequestMapping("/editForm")
    public String edit() {
        return "sap/bapi_form.html";
    }

    /**
     * 查询Bapi列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Bapi> list(Integer page, Integer limit, String searchKey, String searchValue) {
        return bapiService.list(page, limit, searchKey, searchValue);
    }

    /**
     * 添加Bapi
     */
    @ResponseBody
    @RequestMapping("/add")
    public JsonResult add(Bapi bapi) {
        String userName = getLoginUserName();
        bapi.setCreator(userName);
        bapi.setUpdater(userName);
        bapi.setCreateTime(new Date());
        bapi.setUpdateTime(new Date());
        if(bapiService.add(bapi)) {
            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.ok("添加失败");
        }
    }

    /**
     * 修改Bapi
     **/
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(Bapi bapi) {
        String userName = getLoginUserName();
        bapi.setUpdater(userName);
        bapi.setUpdateTime(new Date());
        if(bapiService.update(bapi)) {
            return JsonResult.ok("修改成功");
        } else {
            return JsonResult.ok("修改失败");
        }
    }

    /**
     * 暂停Bapi使用
     */
    @ResponseBody
    @RequestMapping("/pause")
    public JsonResult pause(Integer bapiId) {
        if(bapiService.pause(bapiId)) {
            return JsonResult.ok("暂停成功");
        } else {
            return JsonResult.ok("暂停失败");
        }
    }

    /**
     * 恢复Bapi使用
     */
    @ResponseBody
    @RequestMapping("/resume")
    public JsonResult resume(Integer bapiId) {
        if(bapiService.resume(bapiId)) {
            return JsonResult.ok("恢复成功");
        } else {
            return JsonResult.ok("恢复失败");
        }
    }

    /**
     * 移除Bapi
     */
    @ResponseBody
    @RequestMapping("/remove")
    public JsonResult remove(Integer bapiId) {
        if(bapiService.remove(bapiId)) {
            return JsonResult.ok("移除成功");
        } else {
            return JsonResult.ok("移除失败");
        }
    }

    /**
     * 获取bapi 的参数信息
     */
    @ResponseBody
    @RequestMapping("/getBapiParamsInfo")
    public JsonResult getBapiParamsInfo(String bapiName) {
        Map map = bapiCaller.getFunctionParamsInfo(bapiName);
        return JsonResult.ok().put("data", map);
    }
}
