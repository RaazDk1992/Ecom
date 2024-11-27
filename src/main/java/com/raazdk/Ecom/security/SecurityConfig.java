package com.raazdk.Ecom.security;


import com.raazdk.Ecom.configurations.EcomConf;
import com.raazdk.Ecom.configurations.Oauth2SuccessHandler;
import com.raazdk.Ecom.models.*;
import com.raazdk.Ecom.repository.EcomUserRepository;
import com.raazdk.Ecom.repository.RoleRepository;
import com.raazdk.Ecom.repository.UnitRepository;
import com.raazdk.Ecom.security.jwt.AuthEntryPoint;
import com.raazdk.Ecom.security.jwt.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.LocalDate;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class SecurityConfig {
    @Autowired
    AuthEntryPoint authEntryPoint;

    @Bean
    AuthFilter tokenFilter(){
        return new AuthFilter();
    }

    @Autowired
    @Lazy
    Oauth2SuccessHandler xsuccessHandler;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Value("${front.end}")
    private String fontEnd;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

                http.cors(withDefaults())
                        .csrf(csrf-> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/api/auth/**")
               );
       // http.csrf(csrf->csrf.disable());


        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/getcsrf").permitAll()
                .requestMatchers("/api/user/getuser").hasRole("ADMIN")
                .requestMatchers("api/admin/**").permitAll()
                .anyRequest().authenticated())
                .oauth2Login(o2->o2.successHandler(xsuccessHandler));
        http.exceptionHandling(exception->exception.authenticationEntryPoint(authEntryPoint));
        http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(withDefaults());
        //http.formLogin(withDefaults());
//        http.csrf(csrf->csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/public/**").permitAll() // Public endpoints
//                        .requestMatchers("/api/admin/**").permitAll()
//                        .requestMatchers("/api/getcsrf").permitAll()
//                        .requestMatchers("/uploads/**").permitAll()
//                        .requestMatchers("/api/units/**").permitAll()
//                        .anyRequest().authenticated() // All other requests require authentication
//                )
//                .sessionManagement(sess -> sess
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless JWT
//                )
//                .addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }




    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{

        return configuration.getAuthenticationManager();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository,
                                      EcomUserRepository userRepository,
                                      UnitRepository unitRepository
    ) {
        return args -> {

            Unit unitLtr = unitRepository.findByUnitName(UnitsList.LITER)
                    .orElseGet(() -> unitRepository.save(new Unit(UnitsList.LITER)));
            Unit unitKg = unitRepository.findByUnitName(UnitsList.KG)
                    .orElseGet(() -> unitRepository.save(new Unit(UnitsList.KG)));
            Unit pc = unitRepository.findByUnitName(UnitsList.PIECE)
                    .orElseGet(() -> unitRepository.save(new Unit(UnitsList.PIECE)));
            Unit pair = unitRepository.findByUnitName(UnitsList.PAIR)
                    .orElseGet(() -> unitRepository.save(new Unit(UnitsList.PAIR)));
            Unit dozen = unitRepository.findByUnitName(UnitsList.DOZEN)
                    .orElseGet(() -> unitRepository.save(new Unit(UnitsList.DOZEN)));

            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));

            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

            if (!userRepository.existsByUsername("user")) {
                EcomUser user1 = new EcomUser("user", "user1@mail.com",
                        passwordEncoder().encode("password"));
                user1.setAccountNonLocked(true);
                user1.setAccountNonExpired(true);
                user1.setCredentialsNonExpired(true);
                user1.setEnabled(true);
                user1.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                user1.setAccountExpiryDate(LocalDate.now().plusYears(1));
                user1.setRole(userRole);
                userRepository.save(user1);
            }

            if (!userRepository.existsByUsername("admin")) {
                EcomUser admin = new EcomUser("admin", "admin@mail.com",
                        passwordEncoder().encode("password"));
                admin.setAccountNonLocked(true);
                admin.setAccountNonExpired(true);
                admin.setCredentialsNonExpired(true);
                admin.setEnabled(true);
                admin.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                admin.setAccountExpiryDate(LocalDate.now().plusYears(1));
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        };
    }
}