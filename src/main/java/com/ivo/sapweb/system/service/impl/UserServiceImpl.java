package com.ivo.sapweb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ivo.sapweb.common.PageResult;
import com.ivo.sapweb.common.exception.BusinessException;
import com.ivo.sapweb.common.exception.ParameterException;
import com.ivo.sapweb.common.shiro.EndecryptUtil;
import com.ivo.sapweb.common.util.StringUtil;
import com.ivo.sapweb.system.dao.UserMapper;
import com.ivo.sapweb.system.dao.UserRoleMapper;
import com.ivo.sapweb.system.model.Role;
import com.ivo.sapweb.system.model.User;
import com.ivo.sapweb.system.model.UserRole;
import com.ivo.sapweb.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangjian
 * @date 2018/9/1
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    public PageResult<User> list(int pageNum, int pageSize, boolean showDelete, String column, String value) {
        Wrapper<User> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        // 不显示锁定的用户
        if (!showDelete) {
            wrapper.eq("state", 0);
        }
        Page<User> userPage = new Page<>(pageNum, pageSize);
        List<User> userList = userMapper.selectPage(userPage, wrapper.orderBy("create_time", true));
        if (userList != null && userList.size() > 0) {
            // 查询user的角色
            List<UserRole> userRoles = userRoleMapper.selectByUserIds(getUserIds(userList));
            for (User one : userList) {
                List<Role> tempURs = new ArrayList<>();
                for (UserRole ur : userRoles) {
                    if (one.getUserId().equals(ur.getUserId())) {
                        tempURs.add(new Role(ur.getRoleId(), ur.getRoleName()));
                    }
                }
                one.setRoles(tempURs);
            }
        }
        return new PageResult<>(userPage.getTotal(), userList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean add(User user) throws BusinessException {
        if (userMapper.getByUsername(user.getUsername()) != null) {
            throw new BusinessException("账号已经存在");
        }
        user.setPassword(EndecryptUtil.encrytMd5(user.getPassword(), user.getUsername(), 3));
        user.setState(0);
        user.setCreateTime(new Date());
        boolean rs = userMapper.insert(user) > 0;
        if (rs) {
            List<Integer> roleIds = getRoleIds(user.getRoles());
            if (userRoleMapper.insertBatch(user.getUserId(), roleIds) < roleIds.size()) {
                throw new BusinessException("添加失败，请重试");
            }
        }
        return rs;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(User user) {
        user.setUsername(null);
        boolean rs = userMapper.updateById(user) > 0;
        if (rs) {
            userRoleMapper.delete(new EntityWrapper().eq("user_id", user.getUserId()));
            List<Integer> roleIds = getRoleIds(user.getRoles());
            if (userRoleMapper.insertBatch(user.getUserId(), roleIds) < roleIds.size()) {
                throw new BusinessException("修改失败，请重试");
            }
        }
        return rs;
    }

    /**
     * 添加用户角色
     */
    private List<Integer> getRoleIds(List<Role> roles) {
        List<Integer> rs = new ArrayList<>();
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                rs.add(role.getRoleId());
            }
        }
        return rs;
    }

    @Override
    public boolean updateState(Integer userId, int state) throws ParameterException {
        if (state != 0 && state != 1) {
            throw new ParameterException("state值需要在[0,1]中");
        }
        User user = new User();
        user.setUserId(userId);
        user.setState(state);
        return userMapper.updateById(user) > 0;
    }

    @Override
    public boolean updatePsw(Integer userId, String username, String password) {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(EndecryptUtil.encrytMd5(password, username, 3));
        return userMapper.updateById(user) > 0;
    }

    @Override
    public User getById(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public boolean delete(Integer userId) {
        return userMapper.deleteById(userId) > 0;
    }

    private List<Integer> getUserIds(List<User> userList) {
        List<Integer> userIds = new ArrayList<>();
        for (User one : userList) {
            userIds.add(one.getUserId());
        }
        return userIds;
    }

}
