package com.ivo.sapweb.system.service;

import com.ivo.sapweb.system.model.Authorities;

import java.util.List;

/**
 * @author wangjian
 * @date 2018/9/1
 */
public interface AuthoritiesService {

    List<Authorities> listByUserId(Integer userId);

    List<Authorities> list();

    List<Authorities> listMenu();

    List<Authorities> listByRoleIds(List<Integer> roleId);

    List<Authorities> listByRoleId(Integer roleId);

    boolean add(Authorities authorities);

    boolean update(Authorities authorities);

    boolean delete(Integer authorityId);

    boolean addRoleAuth(Integer roleId, Integer authId);

    boolean deleteRoleAuth(Integer roleId, Integer authId);

    boolean updateRoleAuth(Integer roleId, List<Integer> authIds);
}
