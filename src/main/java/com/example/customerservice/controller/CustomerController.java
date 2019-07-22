package com.example.customerservice.controller;

import com.example.customerservice.integration.CoffeeOrderService;
import com.example.customerservice.integration.CoffeeService;
import com.example.customerservice.model.Coffee;
import com.example.customerservice.model.CoffeeOrder;
import com.example.customerservice.model.OrderRequest;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

  @Autowired
  private CoffeeService coffeeService;
  @Autowired
  private CoffeeOrderService orderService;

  private CircuitBreaker circuitBreaker;

  public CustomerController(CircuitBreakerRegistry registry) {
    this.circuitBreaker = registry.circuitBreaker("menu");
  }

  @GetMapping(path = "/menu")
  public List<Coffee> readMenu() {
    return Try
        .ofSupplier(CircuitBreaker.decorateSupplier(circuitBreaker, () -> coffeeService.getAll()))
        .recover(CircuitBreakerOpenException.class, Collections.emptyList())
        .get();
  }

  @PostMapping(path = "/order")
  @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "order")
  public CoffeeOrder orderCoffee() {
    OrderRequest orderRequest = OrderRequest
        .builder()
        .customer("Kevin Jin")
        .coffeeNames(Arrays.asList("latte", "mocha"))
        .build();
    return orderService.createOrder(orderRequest);
  }
}
