package com.raazdk.Ecom.security.services;

import com.raazdk.Ecom.models.EcomUser;
import com.raazdk.Ecom.repository.EcomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EcomUserDetailsService implements UserDetailsService {
    @Autowired
    EcomUserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EcomUser user = userRepository.findByUsername( username)
                .orElseThrow(()->new UsernameNotFoundException("Username not found!!"));
       return EcomUserDetails.build(user);
    }
}
