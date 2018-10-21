package com.ivo.sapweb.sap.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ivo.sapweb.common.core.Model;

/**
 * bapi的使用记录
 * @author wangjian
 * @date 2018/9/26
 */
@TableName("sap_bapi_record")
public class BapiRecord extends Model {

    /**
     * 主键id
     */
    @TableId
    private Integer id;

    /**
     * bapi
     */
    Integer bapiId;

    /**
     * 是否调用成功
     */
    boolean isSuccess;

    /**
     * ip
     */
    String ip;

    /**
     * 系统
     */
    String os;

    /**
     * 系统类型
     */
    String device;

    /**
     * 浏览器
     */
    String browser;

    /**
     * 耗时
     */
    String timeConsuming;

    @TableField(exist = false)
    String bapiName;

    public String getBapiName() {
        return bapiName;
    }

    public void setBapiName(String bapiName) {
        this.bapiName = bapiName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBapiId() {
        return bapiId;
    }

    public void setBapiId(Integer bapiId) {
        this.bapiId = bapiId;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(String timeConsuming) {
        this.timeConsuming = timeConsuming;
    }
}
