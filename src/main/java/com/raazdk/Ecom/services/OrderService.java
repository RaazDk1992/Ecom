package com.raazdk.Ecom.services;

import com.raazdk.Ecom.dto.OrderSummaryDto;
import com.raazdk.Ecom.dto.OrdersRequestDto;
import com.raazdk.Ecom.models.EcomOrder;
import com.raazdk.Ecom.models.EcomUser;

import java.util.List;
import java.util.Map;

public interface OrderService {


    EcomOrder createOrder(String username,EcomOrder order ,Map<Long, Integer> productDetails);

    List<OrderSummaryDto> getOrderSummary(Long orderId);
}
