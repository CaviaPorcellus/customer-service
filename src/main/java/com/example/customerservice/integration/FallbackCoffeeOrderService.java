package com.example.customerservice.integration;

import com.example.customerservice.model.CoffeeOrder;
import com.example.customerservice.model.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FallbackCoffeeOrderService implements CoffeeOrderService {

  @Override
  public CoffeeOrder getOrder(Long id) {
    return null;
  }

  @Override
  public CoffeeOrder createOrder(OrderRequest orderRequest) {
    log.info("Fallback order service return null");
    return null;
  }
}
