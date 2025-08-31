package com.zse.FinIOAPI.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Autowired
    private JwtUtil jwtUtil;
    
  
    // Endpoints públicos que podem ser acessados sem autenticação
    private static final String[] PUBLIC_MATCHERS = { 
        "/api/login", 
        "/api/v1/usuario/unidadeslogin", 
        "/api/v1/usuario/validate", 
        "/api/v1/usuario/sessaoLogin", 
        "/swagger-ui/**", 
        "/v3/api-docs/**", 
        "/api/v1/resetar-senha/**",
        "/api/posmei/empresa/cadastro"  // ✅ Agora qualquer usuário pode acessar o cadastro de empresa sem login
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, ApiKeyFilter apiKeyFilter) throws Exception {
        AuthenticationManager authManager = http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(authProvider)
            .build();

        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuração de CORS
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API stateless
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_MATCHERS).permitAll()  // ✅ Endpoints públicos liberados
                .anyRequest().authenticated()  // 🔒 Todos os outros endpoints exigem autenticação
            )
            .authenticationManager(authManager) // ✅ Gerenciador de autenticação JWT
            .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class) // ✅ Filtro de API Key
            .addFilter(new JwtAuthenticationFilter(authManager, jwtUtil)) // ✅ Filtro de autenticação JWT
            .addFilter(new JwtAuthorizationFilter(authManager, jwtUtil)); // ✅ Filtro de autorização JWT

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); 
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setMaxAge(1728000L); 
    
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

