package com.example.customerservice.scheduler;

import com.example.customerservice.integration.CoffeeOrderService;
import com.example.customerservice.model.CoffeeOrder;
import com.example.customerservice.model.OrderState;
import com.example.customerservice.model.OrderStateRequest;
import com.example.customerservice.support.OrderWaitingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class CoffeeOrderSchduler {

  @Autowired
  private CoffeeOrderService orderService;

  private Map<Long, CoffeeOrder> orderMap = new ConcurrentHashMap<>();

  @EventListener(OrderWaitingEvent.class)
  public void acceptOrder(OrderWaitingEvent event) {
    CoffeeOrder order = (CoffeeOrder) event.getSource();
    orderMap.put(order.getId(), order);
  }


  @Scheduled(fixedRate = 1000)
  public void waitForCoffee() {
    if (orderMap.isEmpty()) {
      return;
    }

    log.info("I'm waiting for my coffee");
    orderMap.keySet().stream()
        .map(id -> orderService.getOrder(id))
        .filter(order -> OrderState.BREWED == order.getState())
        .forEach(order -> {
          log.info("Order {} is READY, I'll take it.", order);
          orderService.updateOrder(order.getId(),
              OrderStateRequest.builder().state(OrderState.TAKEN).build());
          orderMap.remove(order.getId());
        });
  }
}
