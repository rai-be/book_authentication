package com.jtw.service;

import com.jtw.controller.dto.DtoRequest;
import com.jtw.controller.dto.DtoRespose;
import com.jtw.model.User;
import com.jtw.repository.UserRepository;
import com.jtw.jwt.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceAuth {


        private final UserRepository userRepository;
        private final JwtTokenProvider jwtToken;
        private final PasswordEncoder passwordEncoder;

        public ServiceAuth(UserRepository userRepository, JwtTokenProvider jwtToken, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.jwtToken = jwtToken;
            this.passwordEncoder = passwordEncoder;
        }

        public DtoRespose authenticate(DtoRequest dtoRequest) {
            Optional<User> userOpt = userRepository.findByUsername(dtoRequest.getUsername());

            if (userOpt.isPresent()) {
                User user = userOpt.get();

                if (passwordEncoder.matches(dtoRequest.getPassword(), user.getPassword())) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            user.getUsername(), user.getPassword()
                    );

                    String token = jwtToken.generateToken(authentication);
                    return new DtoRespose(token);
                }
            }

            throw new RuntimeException("Usuário ou senha inválidos");
        }
    }


