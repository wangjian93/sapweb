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

    private String description;

    /**
     * 0锁定， 1正常
     */
    private String state;

    /**
     * 所属类别
     */
    private String group;



}
