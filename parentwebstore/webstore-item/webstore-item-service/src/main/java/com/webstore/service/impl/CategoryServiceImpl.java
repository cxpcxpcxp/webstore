package com.webstore.service.impl;

import com.webstore.item.pojo.Category;
import com.webstore.mapper.ICategoryMapper;
import com.webstore.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    ICategoryMapper iCategoryMapper;
    @Override
    public List<Category> queryCategoriesByPid(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        return iCategoryMapper.select(category);
    }

    @Override
    public List<String> findCnameByIds(List<Long> asList) {
        List<Category> lists = iCategoryMapper.selectByIdList(asList);
        List<String> cname = new ArrayList<>();

        for (Category category : lists) {
            cname.add(category.getName());
        }
        return cname;
    }
}
