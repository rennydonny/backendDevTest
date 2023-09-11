package com.klagan.product.business.usecase.similar;

import static java.util.Objects.isNull;

import com.klagan.product.business.domain.ProductDetail;
import com.klagan.product.business.port.in.ProductFinderPort;
import com.klagan.product.business.port.out.ProductProvider;
import com.klagan.product.business.port.out.SimilarProductProvider;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Getter
@Slf4j
public class ProductFinderUseCase implements ProductFinderPort {

  private final SimilarProductProvider similarProductProvider;
  private final ProductProvider productProvider;

  public ProductFinderUseCase(SimilarProductProvider similarProductProvider,
      ProductProvider productProvider) {
    this.similarProductProvider = similarProductProvider;
    this.productProvider = productProvider;
  }

  public List<ProductDetail> getSimilarProducts(String productId) {
    if (isNull(productId)) {
      log.warn("¡Required parameters are null!");
      return List.of();
    }
    return getSimilarProductProvider().getIds(productId)
        .parallelStream()
        .map(getProductProvider()::findDetail)
        .collect(Collectors.toList());
  }

}
