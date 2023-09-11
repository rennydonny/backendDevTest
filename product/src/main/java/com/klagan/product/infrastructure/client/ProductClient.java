package com.klagan.product.infrastructure.client;

import com.klagan.product.infrastructure.client.response.ProductResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "productClient",
    url = "${klagan.service.product.host}${klagan.service.product.path}",
    configuration = FeignClientsConfiguration.class
)
public interface ProductClient {

  @GetMapping(
      value = "/product/{productId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  ProductResponseDto getProduct(@PathVariable String productId);

  @GetMapping(
      value = "/product/{productId}/similarids",
      produces = MediaType.APPLICATION_JSON_VALUE)
  List<String> getSimilarProducts(@PathVariable String productId);

}
