package com.example.hotelcard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    /*
    
* ( SecurityFilterChain ) Think of it as a chain of security guards sitting in front of your app.
*Every incoming request has to pass through them before it reaches your controllers.
 */    
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        /*
        *  (Cross-Site Request Forgery)
        *CSRF is a type of attack where a malicious website tricks a logged-in user into performing an action on another site.
         */
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/cards/**", "/qr/**").permitAll() // Any request starting with /cards/ or /qr/ is open for everyone (no login required).
                .anyRequest().authenticated() // everything else needs login (Aunthetication)
            )
            .httpBasic(Customizer.withDefaults()); 
        return http.build();
    }
}
