package com.webstore.controller;

import com.webstore.item.pojo.SpecGroup;
import com.webstore.item.pojo.SpecParam;
import com.webstore.service.ISpecGroupService;
import com.webstore.service.ISpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SpecificationController {
    @Autowired
    ISpecGroupService specGroupService;
    @Autowired
    ISpecParamService specParamService;
    @GetMapping("spec/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> findSpecGroupByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> specGroupList = specGroupService.findSpecGroupByCid(cid);
        if(CollectionUtils.isEmpty(specGroupList)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specGroupList);
    }
    @GetMapping("spec/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecsByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> list = this.specParamService.querySpecsByCid(cid);
        if(list == null || list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

//    因为规格参数可以通过很多种方式进行查询 所以可以对该controller进行改造
    @GetMapping("spec/params")
    public ResponseEntity<List<SpecParam>> findSpecParamByGid(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value ="cid",required = false) Long cid,
            @RequestParam(value ="generic",required = false) Boolean generic,
            @RequestParam(value ="searching",required = false) Boolean searching
            ){
        List<SpecParam> specParamList = specParamService.findSpecParamByGid(gid,cid,generic,searching);
        if (CollectionUtils.isEmpty(specParamList)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specParamList);
    }

}
