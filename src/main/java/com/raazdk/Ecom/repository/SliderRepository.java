package com.raazdk.Ecom.repository;

import com.raazdk.Ecom.models.Slider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SliderRepository extends JpaRepository<Slider,Long> {
}
