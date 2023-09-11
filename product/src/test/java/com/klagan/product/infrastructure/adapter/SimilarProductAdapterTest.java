package com.klagan.product.infrastructure.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import com.klagan.product.infrastructure.client.ProductClient;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SimilarProductAdapterTest {

  private ProductClient productClient;
  private SimilarProductAdapter similarProductAdapter;

  @BeforeEach
  void setUp() {
    productClient = mock(ProductClient.class);
    similarProductAdapter = new SimilarProductAdapter(productClient);
  }

  @Test
  void givenProductIdWhenGetIdsThenReturnIds() {
    given(productClient.getSimilarProducts("1")).willReturn(List.of("2", "3", "4"));
    assertNotNull(similarProductAdapter.getIds("1"));
    assertThat(similarProductAdapter.getIds("1")).hasSize(3);
    then(productClient).should(times(2)).getSimilarProducts(anyString());
  }

  @Test
  void givenProductIdWhenGetIdsThenReturnNonResult() {
    given(productClient.getSimilarProducts(anyString())).willReturn(List.of());
    assertNotNull(similarProductAdapter.getIds("1"));
    assertThat(similarProductAdapter.getIds("1")).hasSize(0);
    then(productClient).should(times(2)).getSimilarProducts(anyString());
  }
}