package com.raazdk.Ecom.repository;

import com.raazdk.Ecom.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository  extends JpaRepository<Product,Long> {
}
