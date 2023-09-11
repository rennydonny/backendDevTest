package com.klagan.product.infrastructure.adapter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.klagan.product.infrastructure.client.ProductClient;
import com.klagan.product.infrastructure.client.response.ProductResponseDto;
import com.klagan.product.infrastructure.mapper.ProductMapperImpl;
import feign.FeignException.FeignClientException;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductAdapterTest {

  private ProductClient productClient;
  private ProductMapperImpl mapper;
  private ProductAdapter productAdapter;

  @BeforeEach
  void setUp() {
    productClient = mock(ProductClient.class);
    mapper = new ProductMapperImpl();
    productAdapter = new ProductAdapter(productClient, mapper);
  }

  @Test
  void givenProductIdWhenFindDetailThenReturnShirtProductTest() {
    ProductResponseDto shirt = ProductResponseDto.builder().id("1").name("Shirt")
        .price(BigDecimal.valueOf(9.99)).availability(true).build();
    given(productClient.getProduct("1")).willReturn(shirt);

    assertNotNull(productAdapter.findDetail("1"));
    then(productClient).should(times(1)).getProduct(anyString());
  }

  @Test
  void givenProductIdWhenFindDetailThenReturnNotFoundTest() {
    given(productClient.getProduct(anyString())).willThrow(FeignClientException.class);

    assertThrows(
        FeignClientException.class, () -> productAdapter.findDetail(anyString()));

    then(productClient).should(times(1)).getProduct(anyString());
  }
}