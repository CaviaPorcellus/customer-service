package com.example.customerservice.controller;

import com.example.customerservice.integration.CoffeeOrderService;
import com.example.customerservice.integration.CoffeeService;
import com.example.customerservice.model.Coffee;
import com.example.customerservice.model.CoffeeOrder;
import com.example.customerservice.model.OrderRequest;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

  @Autowired
  private CoffeeService coffeeService;
  @Autowired
  private CoffeeOrderService orderService;

  @GetMapping(path = "/menu")
  @HystrixCommand(fallbackMethod = "readMenuFallBack")
  public List<Coffee> readMenu() {
    return coffeeService.getAll();
  }

  @PostMapping(path = "/order")
  public CoffeeOrder orderCoffee() {
    OrderRequest orderRequest = OrderRequest
        .builder()
        .customer("Kevin Jin")
        .coffeeNames(Arrays.asList("latte", "mocha"))
        .build();
    return orderService.createOrder(orderRequest);
  }

  public List<Coffee> readMenuFallBack() {
    log.info("Fall back to return empty menu");
    return null;
  }
}
