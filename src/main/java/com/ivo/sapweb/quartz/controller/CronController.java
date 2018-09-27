package com.ivo.sapweb.quartz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * cron表达式生成器
 * @author wangjian
 * @date 2018/9/2
 */
@Controller
@RequestMapping("/quartz/cron")
public class CronController {

    @GetMapping
    public String cron() {
        return "quartz/cron.html";
    }
}
