package com.raazdk.Ecom.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

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
    @JoinColumn(name = "category_ref")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "unit_id", referencedColumnName = "unit_id")
    @JsonBackReference
    @ToString.Exclude
    private Unit unit;

}
