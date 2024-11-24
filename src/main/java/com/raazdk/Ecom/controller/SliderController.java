package com.raazdk.Ecom.controller;

import com.raazdk.Ecom.models.Slider;
import com.raazdk.Ecom.services.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/admin/slider")
public class SliderController {

    @Value("${file.upload-dir}")
    private String fileuploadDir;


    @Value("${app.url}")
    private String appUrl;

    @Autowired
    SliderService service;
    @PostMapping("/newslide")
    public ResponseEntity<?> addSlide(@RequestParam String title, @RequestParam MultipartFile imageFile){
        String filePath = "";
        try{
            if(imageFile !=null && !imageFile.isEmpty()) {

                filePath = fileuploadDir+"/slides/"+ imageFile.getOriginalFilename();
                Path path = Paths.get(fileuploadDir+"/slides");

                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                FileOutputStream fos = new FileOutputStream(filePath);

                fos.write(imageFile.getBytes());
            }

            Slider slider = new Slider(title,appUrl+"/slides/"+imageFile.getOriginalFilename());
            service.addSlider(slider);
            return ResponseEntity.ok().body("Slider Added");
        }catch (Exception ex){
            System.out.println("title = " + title + ", imageFile = " + ex.toString());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/getsliders")
    public List<Slider> loadSliders(){
        return service.getSliders();
    }

    @GetMapping("/getslide/{id}")
    public Slider getSlideByList(Long id){
        return service.getSlideByList(id).orElseThrow(()->new RuntimeException("Slide not found"));
    }


}
