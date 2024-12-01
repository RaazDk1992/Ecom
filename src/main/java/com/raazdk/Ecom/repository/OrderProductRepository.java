package com.raazdk.Ecom.repository;

import com.raazdk.Ecom.dto.OrderSummaryDto;
import com.raazdk.Ecom.models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository  extends JpaRepository<OrderProduct,Long> {

    @Query("SELECT new com.raazdk.Ecom.dto.OrderSummaryDto(op.product.productName,op.quantity,op.total)"+
            "FROM OrderProduct op WHERE op.order.OrderId = :orderId")
    List<OrderSummaryDto> findOrderSummaryByOrderId(@Param("orderId") Long orderId);
}
