package com.ivo.sapweb.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ivo.sapweb.common.exception.BusinessException;
import com.ivo.sapweb.system.dao.AuthoritiesMapper;
import com.ivo.sapweb.system.dao.RoleAuthoritiesMapper;
import com.ivo.sapweb.system.model.Authorities;
import com.ivo.sapweb.system.model.RoleAuthorities;
import com.ivo.sapweb.system.service.AuthoritiesService;
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
public class AuthoritiesServiceImpl implements AuthoritiesService {

    @Autowired
    private AuthoritiesMapper authoritiesMapper;
    @Autowired
    private RoleAuthoritiesMapper roleAuthoritiesMapper;

    @Override
    public List<Authorities> listByUserId(Integer userId) {
        return authoritiesMapper.listByUserId(userId);
    }

    @Override
    public List<Authorities> list() {
        return authoritiesMapper.selectList(new EntityWrapper<Authorities>().orderBy("order_number", true));
    }

    @Override
    public List<Authorities> listMenu() {
        return authoritiesMapper.selectList(new EntityWrapper<Authorities>().eq("is_menu", 0).orderBy("order_number", true));
    }

    @Override
    public List<Authorities> listByRoleIds(List<Integer> roleIds) {
        if (roleIds == null || roleIds.size() == 0) {
            return new ArrayList<>();
        }
        return authoritiesMapper.listByRoleIds(roleIds);
    }

    @Override
    public List<Authorities> listByRoleId(Integer roleId) {
        return authoritiesMapper.listByRoleId(roleId);
    }

    @Override
    public boolean add(Authorities authorities) {
        authorities.setCreateTime(new Date());
        return authoritiesMapper.insert(authorities) > 0;
    }

    @Override
    public boolean update(Authorities authorities) {
        return authoritiesMapper.updateById(authorities) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(Integer authorityId) {
        List<Authorities> childs = authoritiesMapper.selectList(new EntityWrapper<Authorities>().eq("parent_id", authorityId));
        if (childs != null && childs.size() > 0) {
            throw new BusinessException("请先删除子节点");
        }
        roleAuthoritiesMapper.delete(new EntityWrapper<RoleAuthorities>().eq("authority_id", authorityId));
        if (authoritiesMapper.deleteById(authorityId) <= 0) {
            throw new BusinessException("删除失败，请重试");
        }
        return true;
    }

    @Override
    public boolean addRoleAuth(Integer roleId, Integer authId) {
        RoleAuthorities roleAuthorities = new RoleAuthorities();
        roleAuthorities.setRoleId(roleId);
        roleAuthorities.setAuthorityId(authId);
        roleAuthorities.setCreateTime(new Date());
        return roleAuthoritiesMapper.insert(roleAuthorities) > 0;
    }

    @Override
    public boolean deleteRoleAuth(Integer roleId, Integer authId) {
        return roleAuthoritiesMapper.delete(new EntityWrapper<RoleAuthorities>().eq("role_id", roleId).eq("authority_id", authId)) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRoleAuth(Integer roleId, List<Integer> authIds) {
        roleAuthoritiesMapper.delete(new EntityWrapper<RoleAuthorities>().eq("role_id", roleId));
        if (authIds != null && authIds.size() > 0) {
            if (roleAuthoritiesMapper.insertRoleAuths(roleId, authIds) < authIds.size()) {
                throw new BusinessException("操作失败");
            }
        }
        return true;
    }
}
