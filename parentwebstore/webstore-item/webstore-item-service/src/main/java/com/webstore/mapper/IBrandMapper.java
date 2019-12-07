package com.webstore.mapper;

import com.webstore.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface IBrandMapper extends Mapper<Brand> {
    @Insert("insert into tb_category_brand values(#{cid},#{bid})")
    public void addCategoryAndBrand(@Param(value = "cid") Long cid, @Param(value = "bid") Long bid);

    @Select("SELECT * FROM tb_brand WHERE id in (SELECT brand_id from tb_category_brand WHERE category_id=#{cid})")
    public List<Brand> findBrandByCid(Long cid);
}
