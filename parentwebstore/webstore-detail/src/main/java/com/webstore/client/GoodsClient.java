package com.webstore.client;

import com.webstore.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "webstore-item-service")
public interface GoodsClient extends GoodsApi {
}
