package com.search.pojo;

import com.webstore.common.pojo.PageResult;
import com.webstore.item.pojo.Brand;

import java.util.List;
import java.util.Map;

public class SearchResult extends PageResult<Goods> {
    private List<Brand> brands;
    private List<Map<String,Object>> categories;

    public SearchResult() {
    }

    public SearchResult(List<Brand> brands, List<Map<String, Object>> categories) {
        this.brands = brands;
        this.categories = categories;
    }

    public SearchResult(Long total, List<Goods> items, List<Brand> brands, List<Map<String, Object>> categories) {
        super(total, items);
        this.brands = brands;
        this.categories = categories;
    }

    public SearchResult(Long total, Integer totalPage, List<Goods> items, List<Brand> brands, List<Map<String, Object>> categories) {
        super(total, totalPage, items);
        this.brands = brands;
        this.categories = categories;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public List<Map<String, Object>> getCategories() {
        return categories;
    }

    public void setCategories(List<Map<String, Object>> categories) {
        this.categories = categories;
    }
}
