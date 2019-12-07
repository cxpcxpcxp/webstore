package com.webstore.service;

import com.webstore.item.pojo.Category;

import java.util.List;

public interface ICategoryService{
    public List<Category> queryCategoriesByPid(Long pid);

    List<String> findCnameByIds(List<Long> asList);
}
