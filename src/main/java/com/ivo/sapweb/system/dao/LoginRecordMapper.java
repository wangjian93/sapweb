package com.ivo.sapweb.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ivo.sapweb.system.model.LoginRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangjian
 * @date 2018/9/3
 */
public interface LoginRecordMapper extends BaseMapper<LoginRecord> {

    /**
     *
     * @param page
     * @param startDate
     * @param endDate
     * @param account
     * @return
     */
    List<LoginRecord> listFull(Page<LoginRecord> page, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("account") String account);

}
