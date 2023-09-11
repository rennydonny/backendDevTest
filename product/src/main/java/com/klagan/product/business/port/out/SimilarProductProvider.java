package com.klagan.product.business.port.out;

import java.util.List;

public interface SimilarProductProvider {

  List<String> getIds(String productId);
}
