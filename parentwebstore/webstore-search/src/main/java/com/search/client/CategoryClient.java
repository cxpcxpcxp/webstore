package com.search.client;

import com.webstore.item.api.CategoryAPi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "webstore-item-service")
public interface CategoryClient extends CategoryAPi {
}
