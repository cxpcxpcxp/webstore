package com.webstore.service;

import com.webstore.common.pojo.PageResult;
import com.webstore.item.pojo.Brand;

import java.util.List;

public interface IBrandService {
    PageResult<Brand> findBrandByPage(String key, Integer page, Integer rows, String bid, Boolean desc);

    void saveBrand(Brand brand, List<Long> cids);

    List<Brand> findBrandByCid(Long cid);
}
