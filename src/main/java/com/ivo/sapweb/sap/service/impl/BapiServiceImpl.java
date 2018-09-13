package com.ivo.sapweb.sap.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ivo.sapweb.common.PageResult;
import com.ivo.sapweb.common.exception.BusinessException;
import com.ivo.sapweb.common.util.StringUtil;
import com.ivo.sapweb.sap.dao.BapiMapper;
import com.ivo.sapweb.sap.model.Bapi;
import com.ivo.sapweb.sap.service.BapiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangjian
 * @date 2018/9/10
 */
@Service
public class BapiServiceImpl implements BapiService {

    @Autowired
    private BapiMapper bapiMapper;

    @Override
    public PageResult<Bapi> list(Integer pageNum, Integer pageSize, String searchKey, String searchValue) {
        Wrapper<Bapi> wrapper = new EntityWrapper<>();
        if(StringUtil.isNotBlank(searchKey) && StringUtil.isNotBlank(searchValue)) {
            wrapper.like(searchKey, searchValue);
        }
        wrapper.eq("is_Valid", true);
        Page<Bapi> page = new Page<>(pageNum, pageSize);
        List<Bapi> list = bapiMapper.selectPage(page, wrapper);
        return new PageResult<>(page.getTotal(), list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(Bapi bapi) {
        Wrapper<Bapi> wrapper = new EntityWrapper<>();
        wrapper.eq("bapi_Name", bapi.getBapiName());
        if(bapiMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("保存失败,'" + bapi.getBapiName() + "'已经存在");
        }
        bapiMapper.insert(bapi);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Bapi bapi) {
        bapiMapper.updateById(bapi);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean pause(Integer bapiId) {
        Bapi bapi = bapiMapper.selectById(bapiId);
        if(bapi == null) {
            throw new BusinessException("操作的Bapi不存在");
        }
        bapi.setState(0);
        bapiMapper.updateById(bapi);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resume(Integer bapiId) {
        Bapi bapi = bapiMapper.selectById(bapiId);
        if(bapi == null) {
            throw new BusinessException("操作的Bapi不存在");
        }
        bapi.setState(1);
        bapiMapper.updateById(bapi);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(Integer bapiId) {
        Bapi bapi = bapiMapper.selectById(bapiId);
        if(bapi == null) {
            throw new BusinessException("操作的Bapi不存在");
        }
        bapi.setValid(false);
        bapiMapper.updateById(bapi);
        return true;
    }
}
