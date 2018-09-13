package com.ivo.sapweb.sap.service;

import com.ivo.sapweb.common.PageResult;
import com.ivo.sapweb.sap.model.Bapi;

/**
 * @author wangjian
 * @date 2018/9/10
 */
public interface BapiService {

    PageResult<Bapi> list(Integer page, Integer limit, String searchKey, String searchValue);

    boolean add(Bapi bapi);

    boolean update(Bapi bapi);

    boolean pause(Integer bapiId);

    boolean resume(Integer bapiId);

    boolean remove(Integer bapiId);
}
