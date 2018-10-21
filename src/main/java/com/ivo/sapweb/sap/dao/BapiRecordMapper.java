package com.ivo.sapweb.sap.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ivo.sapweb.sap.model.BapiRecord;
import com.ivo.sapweb.system.model.LoginRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangjian
 * @date 2018/9/26
 */
public interface BapiRecordMapper extends BaseMapper<BapiRecord> {

    List<BapiRecord> list(Page<BapiRecord> page, @Param("bapiName") String bapiName);

}
