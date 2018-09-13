package com.ivo.sapweb.sap.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ivo.sapweb.common.core.Model;

/**
 * @author wangjian
 * @date 2018/9/10
 */
@TableName("sap_bapi")
public class Bapi extends Model {

    /**
     * 主键id
     */
    @TableId
    private Integer bapiId;

    private String bapiName;

    private String description;

    /**
     * 0锁定， 1正常
     */
    private Integer state;

    /**
     * 所属类别
     */
    private String group;

    /**
     * 使用计数
     */
    private Integer count = 0;

    public String getBapiName() {
        return bapiName;
    }

    public void setBapiName(String bapiName) {
        this.bapiName = bapiName;
    }

    public Integer getBapiId() {
        return bapiId;
    }

    public void setBapiId(Integer bapiId) {
        this.bapiId = bapiId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
