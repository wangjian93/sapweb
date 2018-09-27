package com.ivo.sapweb.system.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.ivo.sapweb.common.shiro.MySessionListener;
import com.ivo.sapweb.system.service.LoginRecordService;
import com.mchange.v2.log.LogUtils;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * @author wangjian
 * @date 2018/8/31
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private LoginRecordService loginRecordService;

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private DataSource druidDataSource;

    @Autowired
    private Scheduler scheduler;

    /**
     * 控制台
     */
    @GetMapping("/console")
    public String console(Model model) {

        int threadPoolSize = 0;
        int numberOfJobsExecuted = 0;

        try {
            threadPoolSize = scheduler.getMetaData().getThreadPoolSize();

            numberOfJobsExecuted = scheduler.getMetaData().getNumberOfJobsExecuted();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        int activeCount = ((DruidDataSource)druidDataSource).getActiveCount();
        int poolingCount = ((DruidDataSource) druidDataSource).getPoolingCount();

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        //椎内存使用情况
        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();

        //初始的总内存
        long totalMemorySize = memoryUsage.getInit();

        //最大可用内存
        long maxMemorySize = memoryUsage.getMax();

        //已使用的内存
        long usedMemorySize = memoryUsage.getUsed();

        String totalMemory = totalMemorySize/(1024*1024)+"M";
        String freeMemory = (totalMemorySize-usedMemorySize)/(1024*1024)+"M";
        String maxMemory = maxMemorySize/(1024*1024)+"M";
        String usedMemory = usedMemorySize/(1024*1024)+"M";

        Integer count = loginRecordService.count();
        Integer activeSessions = sessionDAO.getActiveSessions().size();

        model.addAttribute("threadPoolSize", threadPoolSize);
        model.addAttribute("numberOfJobsExecuted", numberOfJobsExecuted);
        model.addAttribute("activeCount", activeCount);
        model.addAttribute("poolingCount", poolingCount);
        model.addAttribute("count", count);
        model.addAttribute("activeSessions", activeSessions);
        model.addAttribute("usedMemory", usedMemory);
        model.addAttribute("maxMemory", maxMemory);

        return "home/console.html";
    }

    /**
     * 消息弹窗
     */
    @GetMapping("/message")
    public String message() {
        return "tpl/message.html";
    }

    /**
     * 修改密码弹窗
     */
    @GetMapping("/password")
    public String password() {
        return "tpl/password.html";
    }

    /**
     * 主题设置弹窗
     */
    @GetMapping("/theme")
    public String theme() {
        return "tpl/theme.html";
    }

    /**
     * 设置主题
     */
    @GetMapping("/setTheme")
    public String setTheme(String themeName, HttpServletRequest request) {
        if (null == themeName) {
            request.getSession().removeAttribute("theme");
        } else {
            request.getSession().setAttribute("theme", themeName);
        }
        return "redirect:/";
    }
}
