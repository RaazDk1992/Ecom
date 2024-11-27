package com.raazdk.Ecom.repository;

import com.raazdk.Ecom.models.Unit;
import com.raazdk.Ecom.models.UnitsList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit,Long> {
    Optional<Unit> findByUnitName(UnitsList unit);
}
