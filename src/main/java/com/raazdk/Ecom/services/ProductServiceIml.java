package com.raazdk.Ecom.services;


import com.raazdk.Ecom.models.Category;
import com.raazdk.Ecom.models.Product;
import com.raazdk.Ecom.repository.CategoryRepository;
import com.raazdk.Ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceIml  implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public String addProduct(Product product) {
        Category cat = categoryRepository.findById(product.getCategory().getCategoryId())
           .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category could not be found!!"));
        product.setCategory(cat);
        productRepository.save(product);
        return ("Item added");
    }

    @Override
    public String editProduct(Product product) {
        return "";
    }

    @Override
    public String deleteProduct(Long productId) {
        return "";
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
