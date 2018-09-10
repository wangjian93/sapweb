package com.ivo.sapweb.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ivo.sapweb.system.model.User;

/**
 * @author wangjian
 * @date 2018/9/1
 */
public interface UserMapper extends BaseMapper<User> {

    User getByUsername(String username);
}
