package com.webstore.client;

import com.webstore.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "webstore-item-service")
public interface BrandClient extends BrandApi {
}
