package com.raazdk.Ecom.repository;

import com.raazdk.Ecom.models.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit,Long> {
}
