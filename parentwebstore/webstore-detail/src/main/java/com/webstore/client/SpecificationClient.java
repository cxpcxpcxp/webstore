package com.webstore.client;

import com.webstore.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "webstore-item-service")
public interface SpecificationClient extends SpecificationApi {
}
