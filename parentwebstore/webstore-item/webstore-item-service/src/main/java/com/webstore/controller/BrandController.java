package com.webstore.controller;

import com.webstore.common.pojo.PageResult;
import com.webstore.item.pojo.Brand;
import com.webstore.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    IBrandService brandService;
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> findBrandByPage
            (@RequestParam(name = "key"   ,required = false) String key,
             @RequestParam(name = "page"  , defaultValue = "1") Integer page,
             @RequestParam(name = "rows"  , defaultValue = "5") Integer rows,
             @RequestParam(name = "sortBy", required = false) String sortBy,
             @RequestParam(name = "desc"  , required = false) Boolean desc){
        PageResult<Brand> brandByPage = brandService.findBrandByPage(key,page,rows,sortBy,desc);
        if (CollectionUtils.isEmpty(brandByPage.getItems())){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(brandByPage);
    }
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand,@RequestParam(name = "cids") List<Long> cids){
        brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> findBrandByCid(@PathVariable("cid") Long cid){
        List<Brand> brandList = brandService.findBrandByCid(cid);
        if (CollectionUtils.isEmpty(brandList)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brandList);
    }

    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id){
        Brand brand = this.brandService.queryBrandById(id);
        if(brand==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brand);
    }

}
