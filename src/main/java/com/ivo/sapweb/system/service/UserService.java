package com.ivo.sapweb.system.service;

import com.ivo.sapweb.common.PageResult;
import com.ivo.sapweb.common.exception.BusinessException;
import com.ivo.sapweb.common.exception.ParameterException;
import com.ivo.sapweb.system.model.User;

/**
 * @author wangjian
 * @date 2018/9/1
 */
public interface UserService {

    User getByUsername(String username);

    PageResult<User> list(int pageNum, int pageSize, boolean showDelete, String searchKey, String searchValue);

    User getById(Integer userId);

    boolean add(User user) throws BusinessException;

    boolean update(User user);

    boolean updateState(Integer userId, int state) throws ParameterException;

    boolean updatePsw(Integer userId, String username, String newPsw);

    boolean delete(Integer userId);

}
