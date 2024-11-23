package com.raazdk.Ecom.services;

import com.raazdk.Ecom.models.Slider;

import java.util.List;
import java.util.Optional;

public interface SliderService  {
    public Slider addSlider(Slider slider);

    List<Slider> getSliders();

    Optional<Slider> getSlideByList(Long id);
}
