package com.raazdk.Ecom.controller;


import com.raazdk.Ecom.models.Category;
import com.raazdk.Ecom.models.Product;
import com.raazdk.Ecom.repository.CategoryRepository;
import com.raazdk.Ecom.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Controller takes care of http requests coming from client side and
 * calls service layer methods.
 */
@RestController
public class ProductController {
    /**
     * Service Responsible for managing categories.
     */
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;


    //Path for storing uploads
    @Value("${file.upload-dir}")
    private  String UPLOAD_DIRECTORY ;

    @Value("${app.url}")
    private String appUrl;



    /**
     * Endpoint for getting all products details.
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/api/public/products")
    public List<Product> getCategoryList() {
        return productService.getProducts();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/api/admin/addproduct")
    public ResponseEntity<String> addProduct(
            @RequestParam("productName") String productName,
            @RequestParam("manufacturer") String manufacturer,
            @RequestParam("manufactureDate") String manufactureDate,
            @RequestParam("expiryDate") String expiryDate,
            @RequestParam("doesExpire") Boolean doesExpire,
            @RequestParam("quantity") int quantity,
            @RequestParam("price") int price,
            @RequestParam("minQuantity") int minQuantity,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("imageFile") MultipartFile imageFile
) {
        //System.out.println("productName = " + productName);
       try{
           String folderPath = "uploads";
           SimpleDateFormat fromat = new SimpleDateFormat("dd-MM-yyyy");
           String currentInstant  =Instant.now().toString().replace(':','_');

           Product product = new Product();
           product.setProductName(productName);
           product.setManufacturer(manufacturer);
           product.setManufactureDate(fromat.parse(manufactureDate));
           if(expiryDate != null && !expiryDate.isEmpty()){
               product.setExpiryDate(fromat.parse(expiryDate));

           }
           product.setDoesExpire(doesExpire);
           product.setQuantity(quantity);
           product.setPrice(price);
           product.setRatings(Math.random()*5);
           product.setMinQuantity(minQuantity);
           Category cat = categoryRepository.findById(categoryId)
                   .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category could not be found!!"));
           product.setCategory(cat);
            if(imageFile !=null && !imageFile.isEmpty()) {

                String filePath = UPLOAD_DIRECTORY+"/"+ cat.getCategoryName() + "/" +currentInstant+ imageFile.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIRECTORY+"/"+cat.getCategoryName());

                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                FileOutputStream fos = new FileOutputStream(filePath);

                fos.write(imageFile.getBytes());
                System.out.println( "imageFile =" + filePath);
                    product.setImagePath(appUrl+"/"+cat.getCategoryName()+"/"+currentInstant+imageFile.getOriginalFilename());
            }
           return new ResponseEntity<>(productService.addProduct(product),HttpStatus.OK);
       }catch(ResponseStatusException e){
           return new ResponseEntity<>(e.getReason(),e.getStatusCode());

       } catch (FileNotFoundException e) {
           throw new RuntimeException(e);
       } catch (IOException | ParseException e) {
           throw new RuntimeException(e);
       }

    }
}

