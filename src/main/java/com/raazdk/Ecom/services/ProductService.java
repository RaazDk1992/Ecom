package com.raazdk.Ecom.services;


import com.raazdk.Ecom.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public String addProduct(Product product);
    public String editProduct(Product product);
    public String deleteProduct(Long productId);
    public List<Product> getProducts();
}
