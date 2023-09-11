package com.klagan.product.business.port.out;

import com.klagan.product.business.domain.ProductDetail;

public interface ProductProvider {

  ProductDetail findDetail(String id);
}
