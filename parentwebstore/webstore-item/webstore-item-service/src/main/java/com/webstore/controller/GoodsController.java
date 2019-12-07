package com.webstore.controller;

import com.webstore.common.pojo.PageResult;
import com.webstore.item.pojo.SpuBo;
import com.webstore.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
