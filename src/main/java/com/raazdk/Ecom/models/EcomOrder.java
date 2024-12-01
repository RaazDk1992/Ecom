package com.raazdk.Ecom.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class EcomOrder {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long OrderId;

    private LocalDateTime orderDate;

    private double total;



    @ManyToOne
    @JoinColumn(name = "users_id",nullable = false)
    @JsonBackReference("user")
    private EcomUser user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("order-orderproduct")
    private Set<OrderProduct> orderProducts = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EcomOrder order = (EcomOrder) o;
        return Objects.equals(OrderId, order.OrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(OrderId);
    }



}
