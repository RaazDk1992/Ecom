package com.raazdk.Ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Slider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sliderId;

    public Slider(String sliderTitle, String sliderImagePath) {
        this.sliderTitle = sliderTitle;
        this.sliderImagePath = sliderImagePath;
    }

    private String sliderTitle;
    private String sliderImagePath;
}
