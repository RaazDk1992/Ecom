package com.raazdk.Ecom.services;

import com.raazdk.Ecom.dto.OrderSummaryDto;
import com.raazdk.Ecom.models.EcomOrder;
import com.raazdk.Ecom.models.EcomUser;
import com.raazdk.Ecom.models.OrderProduct;
import com.raazdk.Ecom.models.Product;
import com.raazdk.Ecom.repository.EcomOrderRepository;
import com.raazdk.Ecom.repository.OrderProductRepository;
import com.raazdk.Ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OrderServiceIml implements OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EcomOrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private EcomUserService userService;

    @Override
    public EcomOrder createOrder(String username, EcomOrder order, Map<Long, Integer> productDetails) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null.");
        }

        // Fetch user
        EcomUser user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for username: " + username));
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        // Calculate total and prepare OrderProduct entities
        Set<OrderProduct> orderProducts = new HashSet<>();
        double grandTotal = 0;

        for (Map.Entry<Long, Integer> entry : productDetails.entrySet()) {
            Long productId = entry.getKey();
            int quantity = entry.getValue();


            // Validate product existence
            Product product = productRepository.findProductByproductId(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found for ID: " + productId));

            double total = product.getPrice() * quantity;
            grandTotal += total;

            // Create and link OrderProduct
            OrderProduct op = new OrderProduct();
            op.setProduct(product);
            op.setOrder(order);
            op.setQuantity(quantity);
            op.setTotal(total);
            orderProducts.add(op);
        }

        order.setOrderProducts(orderProducts);
        order.setTotal(grandTotal);

        // Save the order and associated OrderProducts
        EcomOrder savedOrder = orderRepository.save(order);
        orderProductRepository.saveAll(orderProducts);

        return savedOrder;
    }

    @Override
    public List<OrderSummaryDto> getOrderSummary(Long orderId) {

        return orderProductRepository.findOrderSummaryByOrderId(orderId);
    }
}
