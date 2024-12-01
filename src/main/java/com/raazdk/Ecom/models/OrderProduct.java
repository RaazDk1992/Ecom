package com.raazdk.Ecom.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Link to EcomOrder
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference("order-orderproduct")
    private EcomOrder order;

    // Link to Product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference("product")
    private Product product;

    private int quantity; // Quantity of this product in the order

    private double total; // Total price for this product (quantity * product price)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct that = (OrderProduct) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, order);
    }



}
