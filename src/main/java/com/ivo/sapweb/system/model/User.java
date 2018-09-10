package com.ivo.sapweb.system.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ivo.sapweb.common.core.Model;

import java.util.List;

/**
 * 用户表
 * @author wangjian
 * @date 2018/9/1
 */
@TableName("sys_user")
public class User extends Model {

    /**
     * 主键id
     */
    @TableId
    private Integer userId;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮箱是否验证
     */
    private Integer emailVerified;

    /**
     * 人员id，关联person表，如果是教学系统，则关联学生表和教师表
     */
    private Integer personId;

    /**
     * 人员类型，比如：0学生，1教师
     */
    private Integer personType;

    /**
     * 用户状态，0正常，1锁定
     */
    private Integer state;

    /**
     * 角色
     */
    @TableField(exist = false)
    private List<Role> roles;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Integer emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
