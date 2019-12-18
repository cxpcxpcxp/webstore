package com.webstore.item.api;

import com.webstore.item.pojo.Category;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("category")
public interface CategoryAPi {
    @GetMapping("list")
    public List<Category> queryCategoriesByPid(@RequestParam(name = "pid", defaultValue = "0") Long pid);
    /**
     * 根据分类id的集合查询分类名称的集合
     * @param ids
     * @return
     */
    @GetMapping("names")
    public List<String> queryNamesByIds(@RequestParam("ids") List<Long> ids);
}
