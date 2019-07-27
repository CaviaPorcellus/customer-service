package com.example.customerservice.support;

import com.example.customerservice.model.CoffeeOrder;
import org.springframework.context.ApplicationEvent;

public class OrderWaitingEvent extends ApplicationEvent {

  public OrderWaitingEvent(CoffeeOrder order) {
    super(order);
  }

}
