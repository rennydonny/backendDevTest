package com.klagan.product.business.usecase.similar;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.klagan.product.business.domain.ProductDetail;
import com.klagan.product.business.port.out.ProductProvider;
import com.klagan.product.business.port.out.SimilarProductProvider;
import feign.FeignException.FeignClientException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductFinderUseCaseTest {

  private SimilarProductProvider similarProductProvider;
  private ProductProvider productProvider;

  private ProductFinderUseCase useCase;

  @BeforeEach
  void setUp() {
    similarProductProvider = mock(SimilarProductProvider.class);
    productProvider = mock(ProductProvider.class);
    useCase = new ProductFinderUseCase(similarProductProvider, productProvider);
  }

  @Test
  void givenProductIdWhenGetSimilarProductsThenReturnProducts() {
    ProductDetail dress = ProductDetail.builder().id("2").name("Dress")
        .price(BigDecimal.valueOf(19.99)).availability(true).build();
    ProductDetail blazer = ProductDetail.builder().id("3").name("Blazer")
        .price(BigDecimal.valueOf(29.99)).availability(false).build();
    ProductDetail boots = ProductDetail.builder().id("4").name("Boots")
        .price(BigDecimal.valueOf(39.99)).availability(true).build();
    given(similarProductProvider.getIds("1")).willReturn(List.of("2", "3", "4"));
    given(productProvider.findDetail("2")).willReturn(dress);
    given(productProvider.findDetail("3")).willReturn(blazer);
    given(productProvider.findDetail("4")).willReturn(boots);

    assertThat(useCase.getSimilarProducts("1")).hasSize(3);
    then(similarProductProvider).should(times(1)).getIds(anyString());
    then(productProvider).should(times(3)).findDetail(anyString());
  }

  @Test
  void givenProductIdWhenGetSimilarProductsThenReturnException() {
    given(similarProductProvider.getIds("7")).willThrow(FeignClientException.class);

    assertThrows(
        FeignClientException.class, () -> useCase.getSimilarProducts("7"));
    then(similarProductProvider).should(times(1)).getIds(anyString());
    then(productProvider).should(times(0)).findDetail(anyString());
  }

  @Test
  void givenNonProductIdWhenGetSimilarProductsThenReturnEmptyList() {
    assertThat(useCase.getSimilarProducts(null)).hasSize(0);
    then(similarProductProvider).should(times(0)).getIds(anyString());
    then(productProvider).should(times(0)).findDetail(anyString());
  }
}