package com.example.waiterservice;

import com.example.waiterservice.integration.CoffeeOrderService;
import com.example.waiterservice.integration.CoffeeService;
import com.example.waiterservice.model.CoffeeOrder;
import com.example.waiterservice.model.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
@Slf4j
public class CustomerRunner implements ApplicationRunner {

  @Autowired
  RestTemplate restTemplate;
  @Autowired
  CoffeeService coffeeService;
  @Autowired
  CoffeeOrderService orderService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    readMenu();
    Long orderId = orderCoffee();
    queryOrder(orderId);
  }

  private void readMenu() {
    coffeeService.getAll().forEach(cc -> log.info("Coffee: {}", cc));
  }

  private long orderCoffee() {
    OrderRequest orderRequest = OrderRequest
        .builder()
        .customer("Kevin Jin")
        .coffeeNames(Arrays.asList("latte", "mocha"))
        .build();
    CoffeeOrder order = orderService.createOrder(orderRequest);
    log.info("Coffee order id: {}", order.getId());
    return order.getId();
  }

  private void queryOrder(Long orderId) {
    CoffeeOrder order = orderService.getOrder(orderId);
    log.info("Coffee order: {}", order);
  }

}
