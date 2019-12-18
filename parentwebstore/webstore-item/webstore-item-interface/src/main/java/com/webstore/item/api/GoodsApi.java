package com.webstore.item.api;

import com.webstore.common.pojo.PageResult;
import com.webstore.item.pojo.Sku;
import com.webstore.item.pojo.Spu;
import com.webstore.item.pojo.SpuBo;
import com.webstore.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public interface GoodsApi {
    @GetMapping("spu/{id}")
    public Spu querySpuById(@PathVariable("id") Long id);
    @RequestMapping("spu/page")
    public PageResult<SpuBo> findSpuByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    );


    @GetMapping("spu/detail/{spuid}")
    public SpuDetail findSpuDetailAndSkus(@PathVariable(value = "spuid") Long spuid);

    @GetMapping("sku/list")
    public List<Sku> findSkusBySpuid(@RequestParam("id") Long spuid);

}
