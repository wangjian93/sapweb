package com.ivo.sapweb.sap.service;

import com.ivo.sapweb.common.PageResult;
import com.ivo.sapweb.sap.model.Bapi;

/**
 * @author wangjian
 * @date 2018/9/10
 */
public interface BapiService {

    /**
     * 分页获取Bapi 列表
     * @param page
     * @param limit
     * @param searchKey
     * @param searchValue
     * @return
     */
    PageResult<Bapi> list(Integer page, Integer limit, String searchKey, String searchValue);

    /**
     * 添加Bapi
     * @param bapi
     * @return
     */
    boolean add(Bapi bapi);

    /**
     * 更新Bapi
     * @param bapi
     * @return
     */
    boolean update(Bapi bapi);

    /**
     * 禁用Bapi
     * @param bapiId
     * @return
     */
    boolean pause(Integer bapiId);

    /**
     * 恢复使用Bapi
     * @param bapiId
     * @return
     */
    boolean resume(Integer bapiId);

    /**
     * 移除Bapi
     * @param bapiId
     * @return
     */
    boolean remove(Integer bapiId);
}
