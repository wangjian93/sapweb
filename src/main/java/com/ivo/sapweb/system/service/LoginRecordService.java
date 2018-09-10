package com.ivo.sapweb.system.service;

import com.ivo.sapweb.common.PageResult;
import com.ivo.sapweb.system.model.LoginRecord;

/**
 * @author wangjian
 * @date 2018/9/3
 */
public interface LoginRecordService {

    boolean add(LoginRecord loginRecord);

    PageResult<LoginRecord> list(int pageNum, int pageSize, String startDate, String endDate, String account);

    Integer count();
}
