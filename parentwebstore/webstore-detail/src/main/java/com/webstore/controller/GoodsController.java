package com.webstore.controller;

import com.webstore.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    @GetMapping("item/{id}.html")
    public ModelAndView toItemPage(@PathVariable("id") Long id){
        ModelAndView mv = new ModelAndView();
        Map<String, Object> modelMap = this.goodsService.loadData(id);
        mv.addAllObjects(modelMap);
        mv.setViewName("item");
        return mv;
    }
}
