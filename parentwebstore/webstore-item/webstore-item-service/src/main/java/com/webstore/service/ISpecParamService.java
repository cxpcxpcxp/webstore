package com.webstore.service;

import com.webstore.item.pojo.SpecParam;

import java.util.List;
public interface ISpecParamService {
    List<SpecParam> findSpecParamByGid(Long gid,Long cid,Boolean generic,Boolean searching);
}
