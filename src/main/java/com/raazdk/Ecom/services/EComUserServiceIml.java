package com.raazdk.Ecom.services;


import com.raazdk.Ecom.models.AppRole;
import com.raazdk.Ecom.models.EcomUser;
import com.raazdk.Ecom.models.Role;
import com.raazdk.Ecom.repository.EcomUserRepository;
import com.raazdk.Ecom.repository.RoleRepository;
import com.raazdk.Ecom.security.request.SignupRequest;
import com.raazdk.Ecom.security.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class EComUserServiceIml implements EcomUserService {

    @Autowired
    private EcomUserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public ResponseEntity<?> saveUser(SignupRequest request) {
        // Check for existing username and email

        if (userRepository.existsByUsername(request.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username already in use."));
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email already in use."));
        }

        // Determine role for the new request

        Role role;
        Set<String> strRoles = request.getRole();
        EcomUser user = new EcomUser();
        user.setUsername(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword())); // Encrypt password here

        user.setAccountNonLocked(request.isAccountNonLocked());
        user.setAccountNonExpired(request.isAccountNonExpired());
        user.setCredentialsNonExpired(request.isCredentialsNonExpired());
        user.setEnabled(request.isEnabled());
        user.setCredentialsExpiryDate(request.getCredentialsExpiryDate());
        user.setAccountExpiryDate(request.getAccountExpiryDate());
        user.setTwoFactorEnabled(request.is2faEnabled());

        if (strRoles == null) {  // Default to ROLE_USER if no role is provided
            role = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found"));
        } else {
            // Check if the request requested an admin role or request role
            String roleName = strRoles.iterator().next();
            if ("ROLE_ADMIN".equals(roleName)) {
                role = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found!"));
            } else {
                role = roleRepository.findByRoleName(AppRole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role not found"));
            }
        }

        // Assign role and encode password
        user.setRole(role);

        // Save request to repository
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully."));
    }

    @Override
    public ResponseEntity<?> updatePassword(String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public Optional<EcomUser> findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public ResponseEntity<?> registerUser(EcomUser ecomUser) {
        try {
            userRepository.save(ecomUser);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Exception in registering"+ex.toString());
        }
        return ResponseEntity.ok().body("Added successfully");
    }

    @Override
    public Optional<EcomUser> findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

}
