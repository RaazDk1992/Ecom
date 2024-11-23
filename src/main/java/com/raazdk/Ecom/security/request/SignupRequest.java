package com.raazdk.Ecom.security.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter @Setter
public class SignupRequest {

    private Long userId;
    @NotBlank
    @Size(min = 5, max = 30)
    private String userName;
    @NotBlank
    @Size(min = 8, max = 50)
    private String email;
    @NotBlank
    private String password;

    private String firstName;
    private String lastName;
    private boolean accountNonLocked = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired=true;
    private boolean enabled = true;
    private LocalDate credentialsExpiryDate;
    private LocalDate accountExpiryDate;
    private boolean is2faEnabled = false;
    private Set<String> role;
}
