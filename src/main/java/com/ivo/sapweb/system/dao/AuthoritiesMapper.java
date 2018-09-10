package com.ivo.sapweb.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ivo.sapweb.system.model.Authorities;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangjian
 * @date 2018/9/1
 */
public interface AuthoritiesMapper extends BaseMapper<Authorities> {

    List<Authorities> listByUserId(Integer userId);

    List<Authorities> listByRoleIds(@Param("roleIds") List<Integer> roleIds);

    List<Authorities> listByRoleId(Integer roleId);
}
