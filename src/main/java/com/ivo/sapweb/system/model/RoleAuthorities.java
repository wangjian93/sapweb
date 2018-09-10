package com.ivo.sapweb.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ivo.sapweb.common.core.Model;

import java.util.Date;

/**
 * 角色权限关联表
 * @author wangjian
 * @date 2018/9/1
 */
@TableName("sys_role_authorities")
public class RoleAuthorities extends Model {

    /**
     * 主键，也可以用联合主键，根据个人习惯
     */
    @TableId
    private Integer id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 权限id
     */
    private Integer authorityId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }
}
