package com.raazdk.Ecom.repository;


import com.raazdk.Ecom.models.AppRole;
import com.raazdk.Ecom.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);

}