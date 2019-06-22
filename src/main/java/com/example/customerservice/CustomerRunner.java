package com.example.customerservice;

import com.example.customerservice.model.Coffee;
import com.example.customerservice.model.CoffeeOrder;
import com.example.customerservice.model.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class CustomerRunner implements ApplicationRunner {

  @Autowired
  RestTemplate restTemplate;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    readMenu();
    Long orderId = orderCoffee();
    queryOrder(orderId);
  }

  private void readMenu() {
    ParameterizedTypeReference<List<Coffee>> ptr =
        new ParameterizedTypeReference<List<Coffee>>() {};
    ResponseEntity<List<Coffee>> responseEntity = restTemplate
        .exchange("http://localhost:8080/coffee/", HttpMethod.GET, null, ptr);
    responseEntity.getBody().forEach(cc -> log.info("Coffee: {}", cc));
  }

  private long orderCoffee() {
    OrderRequest orderRequest = OrderRequest
        .builder()
        .customer("Kevin Jin")
        .coffeeNames(Arrays.asList("latte", "mocha"))
        .build();
    RequestEntity<OrderRequest> request = RequestEntity
        .post(UriComponentsBuilder.fromUriString("http://localhost:8080/order/").build(new HashMap<>()))
        .body(orderRequest);
    ResponseEntity<CoffeeOrder> response = restTemplate.exchange(request, CoffeeOrder.class);
    log.info("Response status: {}", response.getStatusCode());
    log.info("Coffee order id: {}", response.getBody().getId());
    return response.getBody().getId();
  }

  private void queryOrder(Long orderId) {
    URI uri = UriComponentsBuilder
        .fromUriString("http://localhost:8080/order/{id}")
        .build(orderId);
    CoffeeOrder order = restTemplate.getForObject(uri, CoffeeOrder.class);
    log.info("Coffee order: {}", order);
  }

}
