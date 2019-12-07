package com.webstore.service;

import com.webstore.common.pojo.PageResult;
import com.webstore.item.pojo.SpuBo;

public interface IGoodsService {
    PageResult<SpuBo> findSpuByPage(String key, Boolean saleable, Integer page, Integer rows);

    void save(SpuBo spuBo);
}
