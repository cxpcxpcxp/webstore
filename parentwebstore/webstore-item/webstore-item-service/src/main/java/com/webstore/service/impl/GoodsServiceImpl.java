package com.webstore.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.webstore.common.pojo.PageResult;
import com.webstore.item.pojo.*;
import com.webstore.mapper.*;
import com.webstore.service.ICategoryService;
import com.webstore.service.IGoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    ISpuMapper spuMapper;

    @Autowired
    IBrandMapper brandMapper;

    @Autowired
    ICategoryService categoryService;

    @Autowired
    ISpuDetailsMapper spuDetailsMapper;

    @Autowired
    ISkuMapper skuMapper;

    @Autowired
    IStockMapper stockMapper;
    @Override
    public PageResult<SpuBo> findSpuByPage(String key, Boolean saleable, Integer page, Integer rows) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        if (saleable!=null){
            criteria.andEqualTo("saleable",saleable);
        }
        PageHelper.startPage(page,rows);
        List<Spu> spuList = spuMapper.selectByExample(example);

        PageInfo<Spu> spuPageInfo = new PageInfo<>(spuList);
        List<SpuBo> spuBos = new ArrayList<>();

        spuList.forEach(spu -> {
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu,spuBo);

            String bname = brandMapper.selectByPrimaryKey(spu.getBrandId()).getName();
            spuBo.setBname(bname);

            List<String> cname = categoryService.findCnameByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(cname,"/"));

            spuBos.add(spuBo);
        });
        PageResult<SpuBo> pageResult = new PageResult<>(spuPageInfo.getTotal(), spuBos);
        return pageResult;
    }

    @Override
    public void save(SpuBo spuBo) {
//        保存spu
//        设置默认值
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());

//        根据前端传入的参数 保存spu
        spuMapper.insertSelective(spuBo);

//        保存spu_details
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());//设置默认值
//       根据前端传入的参数 保存spu_details
        spuDetailsMapper.insertSelective(spuDetail);

//        保存sku和stock
            saveSkuAndStock(spuBo);

    }

    @Override
    public SpuDetail findSpuDetailAndSkus(Long spuid) {
        return spuDetailsMapper.selectByPrimaryKey(spuid);
    }

    @Override
    public List<Sku> findSkusBySpuid(Long spuid) {
        Sku sku = new Sku();
        sku.setSpuId(spuid);
        List<Sku> skuList = skuMapper.select(sku);
        skuList.forEach(sku1 -> {
            Stock stock = stockMapper.selectByPrimaryKey(sku1.getId());
            stock.setStock(stock.getStock());
        });
        return skuList;
    }

    @Override
    public void updateSpuAndSku(SpuBo spuBo) {
        List<Sku> skuList = findSkusBySpuid(spuBo.getId());
        skuList.forEach(sku -> {
            skuMapper.deleteByPrimaryKey(sku.getId());
            stockMapper.deleteByPrimaryKey(sku.getId());
        });

        spuMapper.updateByPrimaryKeySelective(spuBo);
        spuDetailsMapper.updateByPrimaryKeySelective(spuBo.getSpuDetail());
    }

    @Override
    public Spu querySpuById(Long id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    private void saveSkuAndStock(SpuBo spuBo) {
//        List<Sku> skuList = spuBo.getSkuList();
        spuBo.getSkus().forEach(sku -> {
            //        设置默认值
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(spuBo.getCreateTime());
            sku.setLastUpdateTime(spuBo.getLastUpdateTime());
//            新增
            skuMapper.insertSelective(sku);

            //        设置默认值
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());

            //            新增
            stockMapper.insertSelective(stock);
        });
    }
}
