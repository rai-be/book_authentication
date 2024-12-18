package com.jtw.infra;

import com.jtw.jwt.JwtAuthenticationFilter;
import com.jtw.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private JwtTokenProvider jwtToken = null;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtToken = jwtToken;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //O Spring usa BCrypt , que é seguro porque "embaralha" a senha antes de armazená-la.
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) //CSRF (Cross-Site Request Forgery) é desativado porque sua aplicação usa JWT, que é seguro por si só.
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/api/auth/login").permitAll(); //Permite longin sem autenticação
                    authorize.requestMatchers(HttpMethod.POST, "/user").permitAll(); //permite criar usuario
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll(); //O navegador envia uma requisição OPTIONS e, pergunta: "Servidor, eu ( frontend.com) posso acessar sua API ( api.backend.com)
                    authorize.anyRequest().authenticated(); // Exige autenticação para qualquer outra rota.
                }).httpBasic(Customizer.withDefaults());

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
