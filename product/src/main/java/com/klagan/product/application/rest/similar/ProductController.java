package com.klagan.product.application.rest.similar;

import com.klagan.product.application.rest.similar.domain.ProductDetailResponse;
import com.klagan.product.business.port.in.ProductFinderPort;
import com.klagan.product.infrastructure.mapper.ProductMapper;
import feign.FeignException.FeignClientException;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/product")
@Getter
@Slf4j
public class ProductController {

  private final ProductFinderPort productFinderUseCase;
  private final ProductMapper mapper;

  public ProductController(ProductFinderPort productFinderUseCase, ProductMapper mapper) {
    this.productFinderUseCase = productFinderUseCase;
    this.mapper = mapper;
  }

  @GetMapping(value = "/{productId}/similar")
  public List<ProductDetailResponse> getSimilar(@PathVariable String productId) {
    try {
      return getMapper().toResponse(getProductFinderUseCase().getSimilarProducts(productId));
    } catch (FeignClientException fe) {
      log.error("¡Error getting similar products! For class {} . With parameters productId = {} ",
          this.getClass(),
          productId);
      throw new ResponseStatusException(HttpStatus.valueOf(fe.status()));
    } catch (Exception e) {
      log.error("¡Error getting similar products! For class {} . With parameters productId = {} ",
          this.getClass(),
          productId);
      throw e;
    }
  }

}
