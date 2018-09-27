package com.ivo.sapweb.system.controller;

import com.ivo.sapweb.common.JsonResult;
import com.ivo.sapweb.common.PageResult;
import com.ivo.sapweb.common.util.ReflectUtil;
import com.ivo.sapweb.system.model.Authorities;
import com.ivo.sapweb.system.service.AuthoritiesService;
import com.ivo.sapweb.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 权限管理
 * @author wangjian
 * @date 2018/9/3
 */
@Controller
@RequestMapping("/system/authorities")
public class AuthoritiesController {

    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired
    private RoleService roleService;

    @GetMapping()
    public String authorities(Model model) {
        return "system/authorities.html";
    }

    @GetMapping("editForm")
    public String editForm(Model model) {
        List<Authorities> authorities = authoritiesService.listMenu();
        model.addAttribute("authorities", authorities);
        return "system/authorities_form.html";
    }

    /**
     * 查询所有权限
     **/
    @ResponseBody
    @GetMapping("/list")
    public PageResult<Map<String, Object>> list(Integer roleId) {
        List<Map<String, Object>> maps = new ArrayList<>();
        List<Authorities> authorities = authoritiesService.list();
        List<Authorities> roleAuths = authoritiesService.listByRoleId(roleId);
        for (Authorities one : authorities) {
            Map<String, Object> map = ReflectUtil.objectToMap(one);
            map.put("checked", 0);
            for (Authorities roleAuth : roleAuths) {
                if (one.getAuthorityId().equals(roleAuth.getAuthorityId())) {
                    map.put("checked", 1);
                    break;
                }
            }
            maps.add(map);
        }
        return new PageResult<>(maps);
    }

    /**
     * 添加权限
     */
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(Authorities authorities) {
        if (authoritiesService.add(authorities)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }

    /**
     * 修改权限
     */
    @ResponseBody
    @PostMapping("/update")
    public JsonResult update(Authorities authorities) {
        if (authoritiesService.update(authorities)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

    /**
     * 删除权限
     */
    @ResponseBody
    @PostMapping("/delete")
    public JsonResult delete(Integer authorityId) {
        if (authoritiesService.delete(authorityId)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}
