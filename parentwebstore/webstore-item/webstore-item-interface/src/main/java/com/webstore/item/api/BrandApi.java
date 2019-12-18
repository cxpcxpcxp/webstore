package com.webstore.item.api;

import com.webstore.common.pojo.PageResult;
import com.webstore.item.pojo.Brand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("brand")
public interface BrandApi {
    @GetMapping("page")
    public PageResult<Brand> findBrandByPage
            (@RequestParam(name = "key", required = false) String key,
             @RequestParam(name = "page", defaultValue = "1") Integer page,
             @RequestParam(name = "rows", defaultValue = "5") Integer rows,
             @RequestParam(name = "sortBy", required = false) String sortBy,
             @RequestParam(name = "desc", required = false) Boolean desc);

    @GetMapping("cid/{cid}")
    public List<Brand> findBrandByCid(@PathVariable("cid") Long cid);

    @GetMapping("{id}")
    public Brand queryBrandById(@PathVariable("id") Long id);
}
