package com.academia.sistemaMatricula.service;

import com.academia.sistemaMatricula.model.User;
import reactor.core.publisher.Mono;

public interface IUserService extends ICRUD<User, String> {

    Mono<User> saveHash(User user);
    Mono<com.academia.sistemaMatricula.security.User> searchByUser(String name);

}
