package com.webstore.controller;

import com.webstore.common.pojo.PageResult;
import com.webstore.item.pojo.Sku;
import com.webstore.item.pojo.Spu;
import com.webstore.item.pojo.SpuBo;
import com.webstore.item.pojo.SpuDetail;
import com.webstore.service.IGoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GoodsController {
    @Autowired
    IGoodsService goodsService;
    @RequestMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> findSpuByPage(
            @RequestParam(value = "key",required = false) String key,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows
    ){
        PageResult<SpuBo> pageResult = goodsService.findSpuByPage(key,saleable,page,rows);
        if (CollectionUtils.isEmpty(pageResult.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pageResult);
    }

    @PostMapping("goods")
    public ResponseEntity<Void> save(@RequestBody SpuBo spuBo){
        goodsService.save(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("spu/detail/{spuid}")
    public ResponseEntity<SpuDetail> findSpuDetailAndSkus(@PathVariable Long spuid){
        SpuDetail spuDetail = goodsService.findSpuDetailAndSkus(spuid);
            if (spuDetail==null){
                ResponseEntity.notFound().build();
            }
        return ResponseEntity.ok(spuDetail);

    }

    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> findSkusBySpuid(@RequestParam("id") Long spuid){
        List<Sku> skuList = goodsService.findSkusBySpuid(spuid);
        if (CollectionUtils.isEmpty(skuList)){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skuList);
    }

    @PutMapping("goods")
    public ResponseEntity<Void> updateSpuAndSku(@RequestBody SpuBo spuBo){
        goodsService.updateSpuAndSku(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("spu/{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id){
        Spu spu = this.goodsService.querySpuById(id);
        if(spu == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(spu);
    }

}
