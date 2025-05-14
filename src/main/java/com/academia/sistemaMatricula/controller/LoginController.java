package com.academia.sistemaMatricula.controller;

import com.academia.sistemaMatricula.security.AuthRequest;
import com.academia.sistemaMatricula.security.AuthResponse;
import com.academia.sistemaMatricula.security.JwtUtil;
import com.academia.sistemaMatricula.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;
@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final JwtUtil jwtUtil;
    private final IUserService service;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest authRequest){
        log.info("Entro aqui");
        return service.searchByUser(authRequest.getUsername())
                .map(userDetails -> {
                    if (BCrypt.checkpw(authRequest.getPassword(), userDetails.getPassword())) {
                        String token = jwtUtil.generateToken(userDetails);
                        Date expirationDate = jwtUtil.getExpirationDateFromToken(token);

                        return ResponseEntity.ok(new AuthResponse(token,expirationDate));
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
