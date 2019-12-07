package com.webstore.item.pojo;

import java.io.Serializable;
import java.util.List;

public class SpuBo extends Spu implements Serializable {
    String cname;// 商品分类名称
    String bname;// 品牌名称

    SpuDetail spuDetail;
    List<Sku> skus;

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }





    public String getCname() {
        return cname;
    }
    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
