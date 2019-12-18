package com.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.search.client.BrandClient;
import com.search.client.CategoryClient;
import com.search.client.GoodsClient;
import com.search.client.SpecificationClient;
import com.search.pojo.Goods;
import com.search.pojo.SearchRequest;
import com.search.pojo.SearchResult;
import com.search.repository.GoodsRepository;
import com.webstore.common.pojo.PageResult;
import com.webstore.item.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class GoodsService {
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    BrandClient brandClient;
    @Autowired
    CategoryClient categoryClient;
    @Autowired
    GoodsClient goodsClient;
    @Autowired
    SpecificationClient specificationClient;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Goods buildGoods(Spu spu) throws Exception{

//        创建一个good实例对象
        Goods goods = new Goods();
//        从数据库中查询品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
//        从数据中查询品牌分类
        List<String> queryNamesByIds = categoryClient.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
//        查询所有skus
        List<Sku> skusBySpuid = goodsClient.findSkusBySpuid(spu.getId());
//        定义一个集合来存储price价格
        ArrayList<Long> prices = new ArrayList<>();
//        定义一个list集合来存储skus名称和值
        ArrayList<Map<String,Object>> skusMapList = new ArrayList<>();
//        集合遍历
        skusBySpuid.forEach(sku -> {
            prices.add(sku.getPrice());

//            定义一个map集合来存储相应的键值对
            Map<String, Object> skusHashMap = new HashMap<>();
            skusHashMap.put("id", sku.getId());
            skusHashMap.put("title",sku.getTitle());
            skusHashMap.put("price",sku.getPrice());
            skusHashMap.put("image", StringUtils.isNotBlank(sku.getImages()) ? StringUtils.split(sku.getImages(), ",")[0] : "");

//            加入集合
            skusMapList.add(skusHashMap);
        });

//        查询spudetails
        SpuDetail spuDetail = goodsClient.findSpuDetailAndSkus(spu.getId());
//        查询所有规格参数
        List<SpecParam> specParamByGid = specificationClient.findSpecParamByGid(null, spu.getCid3(), null, true);
//        获取通用的规格参数
        Map<Long, Object> genericSpecMap = MAPPER.readValue(spuDetail.getGenericSpec(), new TypeReference<Map<Long, Object>>(){});
//        获取特殊的规格参数
        Map<Long,List<Object>> specialSpecMap = MAPPER.readValue(spuDetail.getSpecialSpec(),new TypeReference<Map<Long,List<Object>>>(){});

//        定义Map接收 规格参数名称，规格参数值
        Map<String, Object> paramMap = new HashMap<>();
        specParamByGid.forEach(specParam -> {
//            进行相关的判断
            if (!specParam.getNumeric()){
//                是通用规格参数
                String value = genericSpecMap.get(specParam.getId())+toString();
                // 判断是否是数值类型
                if (specParam.getNumeric()){
                    // 如果是数值的话，判断该数值落在那个区间
                    value = chooseSegment(value, specParam);
                }
                // 把参数名和值放入结果集中
                paramMap.put(specParam.getName(), value);
            }
            else {
                List<Object> objects = specialSpecMap.get(specParam.getId());
                paramMap.put(specParam.getName(),objects);
            }
        });

        // 设置参数
        goods.setId(spu.getId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setBrandId(spu.getBrandId());
        goods.setCreateTime(spu.getCreateTime());
        goods.setSubTitle(spu.getSubTitle());
        goods.setAll(spu.getTitle() + brand.getName() + StringUtils.join(queryNamesByIds, " "));
        goods.setPrice(prices);
        goods.setSkus(MAPPER.writeValueAsString(skusMapList));
        goods.setSpecs(paramMap);

        return goods;
    }
    private String chooseSegment(String value, SpecParam p){
        double v = NumberUtils.toDouble(value);
        String result = "其他";
//       获取数值段
        String[] split = p.getSegments().split(",");
        for (String segment : split) {
//            再分割
            String[] segs = segment.split("-");

//            获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;


            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(v >= begin && v < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;


    }

    public PageResult<Goods> search(SearchRequest request) {
        String key = request.getKey();
        // 判断是否有搜索条件，如果没有，直接返回null。不允许搜索全部商品
        if (StringUtils.isBlank(key)) {
            return null;
        }
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        // 1、对key进行全文检索查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", key).operator(Operator.AND));

        // 2、通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle
        queryBuilder.withSourceFilter(new FetchSourceFilter(
                new String[]{"id","skus","subTitle"}, null));

        // 3、分页
        // 准备分页参数
        int page = request.getPage();
        int size = request.getSize();
        queryBuilder.withPageable(PageRequest.of(page - 1, size));


//       聚合查询brand categories
        String categoryAggName = "categories";
        String brandAggName = "brands";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));
        // 执行搜索，获取搜索的结果集
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>)this.goodsRepository.search(queryBuilder.build());
        // 解析聚合结果集  定义两个封装品牌和分类的方法
        List<Map<String, Object>> categories = getCategoryAggResult(goodsPage.getAggregation(categoryAggName));
        List<Brand> brands = getBrandAggResult(goodsPage.getAggregation(brandAggName));


        // 4、查询，获取结果
        Page<Goods> pageInfo = this.goodsRepository.search(queryBuilder.build());
        // 封装结果并返回  方法里面的数据封装以后传入这个返回结果集里面
        return new SearchResult(pageInfo.getTotalElements(), pageInfo.getTotalPages(), pageInfo.getContent(), brands, categories);
    }

    private List<Brand> getBrandAggResult(Aggregation aggregation) {
        // 处理聚合结果集
        LongTerms terms = (LongTerms)aggregation;
        // 获取所有的品牌id桶
        List<LongTerms.Bucket> buckets = terms.getBuckets();
        // 定义一个品牌集合，搜集所有的品牌对象
        List<Brand> brands = new ArrayList<>();
        // 解析所有的id桶，查询品牌
        buckets.forEach(bucket -> {
            Brand brand = this.brandClient.queryBrandById(bucket.getKeyAsNumber().longValue());
            brands.add(brand);
        });
        return brands;
    }

    private List<Map<String, Object>> getCategoryAggResult(Aggregation aggregation) {
        // 处理聚合结果集
        LongTerms terms = (LongTerms)aggregation;
        // 获取所有的分类id桶
        List<LongTerms.Bucket> buckets = terms.getBuckets();
        // 定义一个品牌集合，搜集所有的品牌对象
        List<Map<String, Object>> categories = new ArrayList<>();
        List<Long> cids = new ArrayList<>();
        // 解析所有的id桶，查询品牌
        buckets.forEach(bucket -> {
            cids.add(bucket.getKeyAsNumber().longValue());
        });
        List<String> names = this.categoryClient.queryNamesByIds(cids);
        for (int i = 0; i < cids.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", cids.get(i));
            map.put("name", names.get(i));
            categories.add(map);
        }
        return categories;
    }
}
