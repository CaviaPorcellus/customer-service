package com.example.waiterservice;

import com.example.waiterservice.model.Coffee;
import com.example.waiterservice.model.CoffeeOrder;
import com.example.waiterservice.model.OrderRequest;
import com.example.waiterservice.swagger_client.api.CoffeeControllerApi;
import com.example.waiterservice.swagger_client.api.CoffeeOrderControllerApi;
import com.example.waiterservice.swagger_client.invoker.ApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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

  @Autowired
  ApiClient apiClient;
  @Autowired
  CoffeeControllerApi coffeerApi;
  @Autowired
  CoffeeOrderControllerApi orderApi;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    apiClient.setBasePath("http://localhost:8080");
    readMenu();
    Long orderId = orderCoffee();
    queryOrder(orderId);
  }

  private void readMenu() {
    List<Coffee> coffees = coffeerApi.getAllUsingGET();
    coffees.forEach(cc -> log.info("Coffee: {}", cc));
  }

  private long orderCoffee() {
    OrderRequest orderRequest = OrderRequest
        .builder()
        .customer("Kevin Jin")
        .coffeeNames(Arrays.asList("latte", "mocha"))
        .build();
    CoffeeOrder order = orderApi.createOrderUsingPOST(orderRequest);
    log.info("Coffee order id: {}", order.getId());
    return order.getId();
  }

  private void queryOrder(Long orderId) {
    CoffeeOrder order = orderApi.getOrderUsingGET(orderId);
    log.info("Coffee order: {}", order);
  }

  private void readMenuUsingRestTemplate() {
    ParameterizedTypeReference<List<Coffee>> ptr =
        new ParameterizedTypeReference<List<Coffee>>() {};
    ResponseEntity<List<Coffee>> responseEntity = restTemplate
        .exchange("http://localhost:8080/coffee/", HttpMethod.GET, null, ptr);
    responseEntity.getBody().forEach(cc -> log.info("Coffee: {}", cc));
  }

  private long orderCoffeeUsingRestTemplate() {
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

  private void queryOrderUsingRestTemplate(Long orderId) {
    URI uri = UriComponentsBuilder
        .fromUriString("http://localhost:8080/order/{id}")
        .build(orderId);
    CoffeeOrder order = restTemplate.getForObject(uri, CoffeeOrder.class);
    log.info("Coffee order: {}", order);
  }

}
