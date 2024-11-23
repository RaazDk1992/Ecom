package com.raazdk.Ecom.services;

import com.raazdk.Ecom.models.Slider;
import com.raazdk.Ecom.repository.SliderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SliderServiceIml implements SliderService {

    @Autowired
    SliderRepository repository;
    @Override
    public Slider addSlider(Slider slider) {
        return repository.save(slider);
    }

    @Override
    public List<Slider> getSliders() {
        return repository.findAll();
    }

    @Override
    public Optional<Slider> getSlideByList(Long id) {
        return repository.findById(id);
    }
}
