package com.webstore.service.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.webstore.common.pojo.PageResult;
import com.webstore.item.pojo.Brand;
import com.webstore.mapper.IBrandMapper;
import com.webstore.service.IBrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
@Transactional
public class BrandServiceImpl implements IBrandService {
    @Autowired
    IBrandMapper brandMapper;
    @Override
    public PageResult<Brand> findBrandByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
//        查询brand表 复杂查询 examples
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
//        模糊查询
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);
        }


        PageHelper.startPage(page,rows);
        if (StringUtils.isNotBlank(sortBy)){
            example.setOrderByClause(sortBy + " " +(desc ? "desc":"asc"));
        }

        List<Brand> brands = brandMapper.selectByExample(example);

        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
        PageResult<Brand> brandPageResult = new PageResult<>(brandPageInfo.getTotal(), brandPageInfo.getList());
        return brandPageResult;
    }

    @Override
    public void saveBrand(Brand brand, List<Long> cids) {
        brandMapper.insertSelective(brand);
        cids.forEach(cid->{
            brandMapper.addCategoryAndBrand(cid,brand.getId());
        });
    }

    @Override
    public List<Brand> findBrandByCid(Long cid) {
        List<Brand> brandByCid = brandMapper.findBrandByCid(cid);
        return brandByCid;
    }
}
