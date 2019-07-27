package com.example.customerservice.integration;

import com.example.customerservice.model.CoffeeOrder;
import com.example.customerservice.model.OrderState;
import com.example.customerservice.model.OrderStateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationListener {

  @Autowired
  private CoffeeOrderService orderService;

  @StreamListener(Waiter.NOTIFY_ORDERS)
  public void takeOrder(Long id) {
    CoffeeOrder order = orderService.getOrder(id);
    if (OrderState.BREWED == order.getState()) {
      orderService.updateState(id,
          OrderStateRequest.builder().state(OrderState.TAKEN).build());
      log.info("Taken coffee: {}", id);
    } else {
      log.warn("Order {} is NOT READY. Why do you notify me ?", id);
    }
  }
}
