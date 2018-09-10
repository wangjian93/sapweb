package com.ivo.sapweb.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ivo.sapweb.system.model.Role;

import java.util.List;

/**
 * @author wangjian
 * @date 2018/9/1
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectByUserId(Integer userId);
}
