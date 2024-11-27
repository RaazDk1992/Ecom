package com.raazdk.Ecom.controller;

import com.raazdk.Ecom.models.EcomUser;
import com.raazdk.Ecom.models.Role;
import com.raazdk.Ecom.security.response.UserInfoResponse;
import com.raazdk.Ecom.services.EcomUserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Console;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    EcomUserService service;

    @GetMapping("/getuser")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserDetails details){

        try {

            EcomUser user = service.findByUsername(details.getUsername()).orElseThrow(()->new UsernameNotFoundException("Provided username does not exist"));

            System.out.println("User "+details.getUsername());
            List<String> roles  = details.getAuthorities().stream().map(item->item.getAuthority()).collect(Collectors.toList());
            UserInfoResponse response = new UserInfoResponse(
                    user.getUserId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.isAccountNonLocked(),
                    user.isAccountNonExpired(),
                    user.isCredentialsNonExpired(),
                    user.isEnabled(),
                    user.getCredentialsExpiryDate(),
                    user.getAccountExpiryDate(),
                    user.isTwoFactorEnabled(),
                    roles

            );

            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getCause(),HttpStatus.BAD_REQUEST);
        }

    }
}
