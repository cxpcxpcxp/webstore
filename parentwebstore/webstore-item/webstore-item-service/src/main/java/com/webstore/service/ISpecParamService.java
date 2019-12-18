package com.webstore.service;

import com.webstore.item.pojo.SpecGroup;
import com.webstore.item.pojo.SpecParam;

import java.util.List;
public interface ISpecParamService {
    List<SpecParam> findSpecParamByGid(Long gid,Long cid,Boolean generic,Boolean searching);

    List<SpecGroup> querySpecsByCid(Long cid);
}
