package com.ivo.sapweb.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ivo.sapweb.common.core.Model;

/**
 * 登录日志
 * @author wangjian
 * @date 2018/9/3
 */
@TableName("sys_login_record")
public class LoginRecord extends Model {

    /**
     * 主键
     */
    @TableId
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 设备型号
     */
    private String device;

    /**
     * 浏览器类型
     */
    private String browserType;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 用户昵称
     */
    @TableField(exist = false)
    private String nickName;

    /**
     * 用户账号
     */
    @TableField(exist = false)
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
