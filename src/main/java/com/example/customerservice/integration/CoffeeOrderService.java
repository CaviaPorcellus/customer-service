package com.example.customerservice.integration;

import com.example.customerservice.model.CoffeeOrder;
import com.example.customerservice.model.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "waiter-service", contextId = "coffeeOrder", path = "/order")
public interface CoffeeOrderService {

  @GetMapping(path = "/{id}")
  CoffeeOrder getOrder(@PathVariable Long id);

  @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
  CoffeeOrder createOrder(@RequestBody OrderRequest orderRequest);

}
