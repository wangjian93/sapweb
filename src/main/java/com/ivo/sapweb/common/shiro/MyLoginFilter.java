package com.ivo.sapweb.common.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * 自定义shiro过滤器
 * @author wangjian
 * @date 2018/9/2
 */
public class MyLoginFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            if (isAjax((HttpServletRequest) servletRequest)) {
                servletResponse.setContentType("application/json;charset=UTF-8");
                PrintWriter out = servletResponse.getWriter();
                out.write("{\"msg\":\"登录过期，请重新登录\",\"code\":401}");
                out.flush();
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是ajax请求
     */
    private boolean isAjax(HttpServletRequest request) {
        String xHeader = request.getHeader("X-Requested-With");
        if (xHeader != null && xHeader.contains("XMLHttpRequest")) {
            return true;
        }
        return false;
    }
}
