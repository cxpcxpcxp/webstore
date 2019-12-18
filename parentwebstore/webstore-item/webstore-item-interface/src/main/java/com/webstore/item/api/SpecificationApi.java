package com.webstore.item.api;

import com.webstore.item.pojo.SpecGroup;
import com.webstore.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public interface SpecificationApi {
    @GetMapping("spec/groups/{cid}")
    public List<SpecGroup> findSpecGroupByCid(@PathVariable("cid") Long cid);

//    因为规格参数可以通过很多种方式进行查询 所以可以对该controller进行改造
    @GetMapping("spec/params")
    public List<SpecParam> findSpecParamByGid(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "searching", required = false) Boolean searching
    );

    @GetMapping("spec/{cid}")
    public List<SpecGroup> querySpecsByCid(@PathVariable("cid") Long cid);

}
