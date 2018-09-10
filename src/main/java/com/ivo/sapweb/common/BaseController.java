package com.ivo.sapweb.common;

import com.ivo.sapweb.system.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Controller基类
 * @author wangjian
 * @date 2018/8/31
 */
public class BaseController {

    /**
     * 获取当前登录的user
     */
    public User getLoginUser() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Object object = subject.getPrincipal();
            if (object != null) {
                return (User) object;
            }
        }
        return null;
    }

    /**
     * 获取当前登录的userId
     */
    public Integer getLoginUserId() {
        User loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getUserId();
    }

    /**
     * 获取当前登录的username
     */
    public String getLoginUserName() {
        User loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getUsername();
    }
}
