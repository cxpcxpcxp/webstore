package com.webstore.service;

import com.webstore.common.pojo.PageResult;
import com.webstore.item.pojo.Sku;
import com.webstore.item.pojo.Spu;
import com.webstore.item.pojo.SpuBo;
import com.webstore.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IGoodsService {
    PageResult<SpuBo> findSpuByPage(String key, Boolean saleable, Integer page, Integer rows);

    void save(SpuBo spuBo);

    SpuDetail findSpuDetailAndSkus(Long spuid);

    List<Sku> findSkusBySpuid(Long spuid);

    void updateSpuAndSku(SpuBo spuBo);

    Spu querySpuById(Long id);
}
