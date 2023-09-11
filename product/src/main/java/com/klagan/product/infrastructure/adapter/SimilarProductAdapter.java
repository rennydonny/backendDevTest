package com.klagan.product.infrastructure.adapter;

import com.klagan.product.business.port.out.SimilarProductProvider;
import com.klagan.product.infrastructure.client.ProductClient;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SimilarProductAdapter implements SimilarProductProvider {

  private final ProductClient productClient;

  public SimilarProductAdapter(ProductClient productClient) {
    this.productClient = productClient;
  }

  @Override
  public List<String> getIds(String productId) {
    return getProductClient().getSimilarProducts(productId);
  }
}
