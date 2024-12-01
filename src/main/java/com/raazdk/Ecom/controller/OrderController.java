package com.raazdk.Ecom.controller;

import com.raazdk.Ecom.dto.OrderSummaryDto;
import com.raazdk.Ecom.dto.OrdersRequestDto;
import com.raazdk.Ecom.models.EcomOrder;
import com.raazdk.Ecom.models.EcomUser;
import com.raazdk.Ecom.models.Product;
import com.raazdk.Ecom.repository.EcomOrderRepository;
import com.raazdk.Ecom.repository.EcomUserRepository;
import com.raazdk.Ecom.repository.ProductRepository;
import com.raazdk.Ecom.services.EcomUserService;
import com.raazdk.Ecom.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);



    @Autowired
    private OrderService orderService;



    @PostMapping("/newOrder")
    public ResponseEntity<?> addOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OrdersRequestDto ordersRequest) {

        try{
            EcomOrder order = orderService.createOrder(userDetails.getUsername(), ordersRequest.getOrder(), ordersRequest.getProductQuantities());
            return  ResponseEntity.ok().body(order.getOrderId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error :"+e.getMessage());
        }

    }

    @GetMapping("/ordersummary/{orderId}")
    public List<OrderSummaryDto> loadSummary(@PathVariable Long orderId){
        return orderService.getOrderSummary(orderId);
    }

}
