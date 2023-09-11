package com.klagan.product.infrastructure.adapter;

import com.klagan.product.business.domain.ProductDetail;
import com.klagan.product.business.port.out.ProductProvider;
import com.klagan.product.infrastructure.client.ProductClient;
import com.klagan.product.infrastructure.mapper.ProductMapper;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ProductAdapter implements ProductProvider {

  private final ProductClient productClient;
  private final ProductMapper mapper;

  public ProductAdapter(ProductClient productClient, ProductMapper mapper) {
    this.productClient = productClient;
    this.mapper = mapper;
  }

  @Override
  public ProductDetail findDetail(String id) {
    return getMapper().fromDto(getProductClient().getProduct(id));
  }
}
