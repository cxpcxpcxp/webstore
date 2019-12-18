package com.search.controller;

import com.search.pojo.Goods;
import com.search.pojo.SearchRequest;
import com.search.pojo.SearchResult;
import com.search.service.GoodsService;
import com.webstore.common.pojo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    @Autowired
    GoodsService goodsService;
    /*@PostMapping("page")
    public ResponseEntity<PageResult<Goods>> search(@RequestBody SearchRequest request){
        PageResult<Goods> pageResult = goodsService.search(request);
        if (pageResult==null){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
    @PostMapping("page")
    public ResponseEntity<PageResult<Goods>> search(@RequestBody SearchRequest request){
        PageResult<Goods> pageResult = goodsService.search(request);
        if (pageResult==null){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pageResult);
    }
}
