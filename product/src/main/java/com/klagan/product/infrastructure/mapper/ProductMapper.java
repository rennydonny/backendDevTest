package com.klagan.product.infrastructure.mapper;

import com.klagan.product.application.rest.similar.domain.ProductDetailResponse;
import com.klagan.product.business.domain.ProductDetail;
import com.klagan.product.infrastructure.client.response.ProductResponseDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring")
public abstract class ProductMapper {

  public abstract ProductDetail fromDto(ProductResponseDto product);

  public abstract ProductDetailResponse from(ProductDetail productDetail);

  public abstract List<ProductDetailResponse> toResponse(List<ProductDetail> similarProducts);
}
