package com.raazdk.Ecom.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
/**
 *
 * Class stores the product details including manufacturer, whether it expires and expiry date.
 */
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long productId;
    String productName;
    String manufacturer;
    String imagePath;
    Date manufactureDate;
    Date expiryDate;
    Boolean doesExpire;
    int quantity;    // Min Quantity to order
    int price;
    int minQuantity; //Min stock quantity
    double ratings;
    int totalRates;
    int itemsInStock; // Stock Quantity


    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference("category-products")
    @JoinColumn(name = "category_ref")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "unit_id", referencedColumnName = "unit_id")
    @JsonBackReference("unit-product")
    @ToString.Exclude
    private Unit unit;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("product")
    private Set<OrderProduct> orderProducts = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
