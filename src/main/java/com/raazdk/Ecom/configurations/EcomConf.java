package com.raazdk.Ecom.configurations;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EcomConf  implements WebMvcConfigurer {


    @Value("${file.upload-fullpath}")
    private String resourceLocation;

    @Value("${front.end}")
    private String frontEndUrl;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(resourceLocation);
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                System.out.println("Cors config: "+frontEndUrl);

                registry.addMapping("/**")
                        .allowedOrigins(frontEndUrl)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("Authorization", "Content-Type", "X-XSRF-TOKEN", "Accept")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }

}