package com.example.waiterservice.integration;

import com.example.waiterservice.model.Coffee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "waiter-service", contextId = "coffee", path = "/coffee")
public interface CoffeeService {

  @GetMapping(path = "/", params = "!name")
  List<Coffee> getAll();

  @GetMapping(path = "/{id}")
  Coffee getById(@PathVariable Long id);

}
