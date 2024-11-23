package com.raazdk.Ecom.controller;


import com.raazdk.Ecom.models.Category;
import com.raazdk.Ecom.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/api/public/categories")
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }


    @PostMapping("/api/admin/addcategory")
    public ResponseEntity<String> addCategory(@RequestBody Category category){
       try{

           String status= categoryService.addCategory(category);
           return new ResponseEntity<>(status,HttpStatus.OK);
       }catch (ResponseStatusException e){
           return new ResponseEntity<>(e.getReason(),e.getStatusCode());
       }

    }

    @DeleteMapping("/api/admin/categories/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        try{
            String status= categoryService.deleteCategory(id);
            return new ResponseEntity<>(status, HttpStatus.OK);
        }catch (ResponseStatusException e){
            System.out.println("error = " + e.getMessage());
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }

    }
    @PutMapping("/api/admin/categories/update")
    public ResponseEntity<String> updateCategory(@RequestBody  Category c){
        try{
            String status= categoryService.updateCategory(c,c.getCategoryId());
            return new ResponseEntity<>(status, HttpStatus.OK);
        }catch (ResponseStatusException e){
            System.out.println("error = " + e.getMessage());
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }
    /**
     * Say Hi to Api
     * @return
     */
    @GetMapping("/api/public/hello")
    public String sayHi(){
        return  "Hi";
    }

}
