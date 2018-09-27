package com.ivo.sapweb.sap.controller;

import com.ivo.sapweb.common.BaseController;
import com.ivo.sapweb.common.JsonResult;
import com.ivo.sapweb.common.PageResult;
import com.ivo.sapweb.sap.model.Bapi;
import com.ivo.sapweb.sap.service.BapiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author wangjian
 * @date 2018/9/10
 */
@Controller
@RequestMapping("/sap/bapi")
public class BapiController extends BaseController {

    @Autowired
    private BapiService bapiService;

    /**
     * 主界面
     * @return
     */
    @GetMapping
    public String bapi() {
        return "sap/bapi.html";
    }

    /**
     * 添加修改界面
     * @return
     */
    @GetMapping("/editForm")
    public String edit() {
        return "sap/bapi_form.html";
    }

    /**
     * bapi访问界面
     * @return
     */
    @GetMapping("/access")
    public String access(Model model, String bapiName) {
        model.addAttribute("bapiName", bapiName);
        return "sap/bapi_access.html";
    }

    /**
     * 查询Bapi列表
     */
    @ResponseBody
    @PostMapping("/list")
    public PageResult<Bapi> list(Integer page, Integer limit, String searchKey, String searchValue) {
        return bapiService.list(page, limit, searchKey, searchValue);
    }

    /**
     * 添加Bapi
     */
    @ResponseBody
    @PostMapping("/add")
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
    @PostMapping("/update")
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
    @PostMapping("/pause")
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
    @PostMapping("/resume")
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
    @PostMapping("/remove")
    public JsonResult remove(Integer bapiId) {
        if(bapiService.remove(bapiId)) {
            return JsonResult.ok("移除成功");
        } else {
            return JsonResult.ok("移除失败");
        }
    }
}
