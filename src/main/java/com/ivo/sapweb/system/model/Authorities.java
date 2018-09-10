package com.ivo.sapweb.system.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ivo.sapweb.common.core.Model;

/**
 * 权限表 (或叫资源表)
 * @author wangjian
 * @date 2018/9/1
 */
@TableName("sys_authorities")
public class Authorities extends Model {

    /**
     * 权限id
     */
    @TableId
    private Integer authorityId;

    /**
     * 权限名称
     */
    private String authorityName;

    /**
     * 权限标识（如果为空不会添加在shiro的权限列表中）
     */
    private String authority;

    /**
     * 菜单url
     */
    private String menuUrl;

    /**
     * 上级菜单
     */
    private Integer parentId;

    /**
     * 菜单还是按钮（菜单会显示在侧导航，按钮不会显示在侧导航，只要url不是空，都会作为权限标识）
     */
    private Integer isMenu;

    /**
     * 排序号
     */
    private Integer orderNumber;

    /**
     * 菜单图标
     */
    private String menuIcon;

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }
}
