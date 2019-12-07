package com.webstore.service;

import com.webstore.item.pojo.SpecGroup;

import java.util.List;

public interface ISpecGroupService {
    List<SpecGroup> findSpecGroupByCid(Long cid);
}
