package com.webstore.mapper;

import com.webstore.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface IBrandMapper extends Mapper<Brand> {
    @Insert("insert into tb_category_brand values(#{cid},#{bid})")
    public void addCategoryAndBrand(@Param(value = "cid") Long cid, @Param(value = "bid") Long bid);
}
