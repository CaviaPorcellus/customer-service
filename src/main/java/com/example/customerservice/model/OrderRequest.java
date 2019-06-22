package com.example.customerservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderRequest {

  private String customer;

  private List<String> coffeeNames;
}
