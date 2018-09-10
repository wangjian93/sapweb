package com.ivo.sapweb.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ivo.sapweb.common.core.Model;

/**
 * 用户角色关联表
 * 如果你的用户只对应一个角色，把前台的多选select改成单选即可，不需要改表结构
 * @author wangjian
 * @date 2018/9/1
 */
@TableName("sys_user_role")
public class UserRole extends Model {

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
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色名称
     */
    @TableField(exist = true)
    private String roleName;

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
}
