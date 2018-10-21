package com.ivo.sapweb.sap.service;

import com.ivo.sapweb.common.PageResult;
import com.ivo.sapweb.sap.model.BapiRecord;

/**
 * @author wangjian
 * @date 2018/10/9
 */
public interface BapiRecordService {

    /**
     * 分页获取bapi使用记录列表
     * @param page
     * @param limit
     * @param searchKey
     * @param searchValue
     * @return
     */
    PageResult<BapiRecord> list(Integer page, Integer limit, String searchKey, String searchValue);

}
