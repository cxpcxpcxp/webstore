package com.webstore.service.impl;

import com.webstore.item.pojo.SpecParam;
import com.webstore.mapper.ISpecParamMapper;
import com.webstore.service.ISpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class SpecParamServiceImpl implements ISpecParamService {
    @Autowired
    ISpecParamMapper specParamMapper;

    @Override
    public List<SpecParam> findSpecParamByGid(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setGeneric(generic);
        specParam.setSearching(searching);
        List<SpecParam> select = specParamMapper.select(specParam);
        return select;
    }
}
