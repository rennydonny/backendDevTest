package com.klagan.product.business.port.in;

import com.klagan.product.business.domain.ProductDetail;
import java.util.List;

public interface ProductFinderPort {

  public List<ProductDetail> getSimilarProducts(String productId);
}
