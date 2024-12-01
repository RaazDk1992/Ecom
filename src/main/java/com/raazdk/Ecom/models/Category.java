package com.raazdk.Ecom.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("category-products")
    private List<Product> products = new ArrayList<>();

}
