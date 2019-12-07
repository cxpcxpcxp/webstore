package com.webstore.service.impl;

import com.webstore.item.pojo.SpecGroup;
import com.webstore.mapper.ISpecGroupMapper;
import com.webstore.service.ISpecGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class SpecGroupServiceImpl implements ISpecGroupService {
    @Autowired
    ISpecGroupMapper specGroupMapper;
    @Override
    public List<SpecGroup> findSpecGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> specGroups = specGroupMapper.select(specGroup);
        return specGroups;
    }
}
