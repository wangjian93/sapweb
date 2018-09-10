package com.ivo.sapweb.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ivo.sapweb.system.model.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangjian
 * @date 2018/9/1
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRole> selectByUserIds(@Param("userIds") List<Integer> userIds);

    int insertBatch(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleIds);

}
