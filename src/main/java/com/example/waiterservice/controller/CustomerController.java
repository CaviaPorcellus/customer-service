package com.example.waiterservice.controller;

import com.example.waiterservice.integration.CoffeeOrderService;
import com.example.waiterservice.integration.CoffeeService;
import com.example.waiterservice.model.Coffee;
import com.example.waiterservice.model.CoffeeOrder;
import com.example.waiterservice.model.OrderRequest;
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
}
