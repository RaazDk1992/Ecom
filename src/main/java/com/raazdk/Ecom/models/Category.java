package com.raazdk.Ecom.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
/**
 * Category Class
 */
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long CategoryId;
    String CategoryName;


    @Entity
    @NoArgsConstructor@AllArgsConstructor
    @Data

    public static class Product {
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        Long productId;
        String productName;
        int price;
        int quantity;
        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "category_ref")
        Category category;

    }
}
