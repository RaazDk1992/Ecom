package com.raazdk.Ecom.repository;

import com.raazdk.Ecom.models.EcomUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EcomUserRepository extends JpaRepository<EcomUser,Long> {

    Optional <EcomUser> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(@NotBlank @Size(min = 8, max = 50) String email);

    Optional<EcomUser> findByEmail(String email);
}