package com.academia.sistemaMatricula.repository;

import com.academia.sistemaMatricula.model.User;
import reactor.core.publisher.Mono;

public interface IUserRepo extends IGenericRepo<User, String> {
    Mono<User> findOneByUsername(String username);
}
