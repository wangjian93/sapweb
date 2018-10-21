package com.ivo.sapweb.sap.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ivo.sapweb.common.PageResult;
import com.ivo.sapweb.common.util.StringUtil;
import com.ivo.sapweb.sap.dao.BapiRecordMapper;
import com.ivo.sapweb.sap.model.BapiRecord;
import com.ivo.sapweb.sap.service.BapiRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangjian
 * @date 2018/10/9
 */
@Service
public class BapiRecordServiceImpl implements BapiRecordService {

    @Autowired
    private BapiRecordMapper bapiRecordMapper;

    @Override
    public PageResult<BapiRecord> list(Integer pageNum, Integer pageSize, String searchKey, String searchValue) {
        Page<BapiRecord> page = new Page<>(pageNum, pageSize);
        List<BapiRecord> list = bapiRecordMapper.list(page, null);;
        return new PageResult<>(page.getTotal(), list);
    }
}
