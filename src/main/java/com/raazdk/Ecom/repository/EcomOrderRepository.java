package com.raazdk.Ecom.repository;

import com.raazdk.Ecom.models.EcomOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcomOrderRepository extends JpaRepository<EcomOrder,Long> {
}
