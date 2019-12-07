package com.webstore.mapper;

import com.webstore.item.pojo.Category;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ICategoryMapper extends Mapper<Category> , SelectByIdListMapper<Category,Long> {
}
