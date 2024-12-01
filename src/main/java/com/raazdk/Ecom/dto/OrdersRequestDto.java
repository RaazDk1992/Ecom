package com.raazdk.Ecom.dto;

import com.raazdk.Ecom.models.EcomOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersRequestDto {
  private EcomOrder order; // Basic order details (e.g., user, date)
  private Map<Long, Integer> productQuantities; // Product ID and quantity
}