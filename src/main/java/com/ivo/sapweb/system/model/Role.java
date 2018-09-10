package com.ivo.sapweb.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ivo.sapweb.common.core.Model;

/**
 * 角色表
 * @author wangjian
 * @date 2018/9/1
 */
@TableName("sys_role")
public class Role extends Model {

    /**
     * 角色id
     */
    @TableId
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 描述
     */
    private String comments;

    /**
     * 逻辑删除，0未删除，1已删除
     */
    private Integer isDelete;

    public Role() {
    }

    public Role(Integer roleId) {
        setRoleId(roleId);
    }

    public Role(Integer roleId, String roleName) {
        setRoleId(roleId);
        setRoleName(roleName);
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
