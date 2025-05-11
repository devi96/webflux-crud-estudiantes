package com.academia.sistemaMatricula.service;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICRUD<T, ID> {
    Mono<T> save(T entity);
    Mono<T> update(ID id, T entity);
    Flux<T> findAll();
    Mono<T> findById(ID id);
    Mono<Boolean> delete(ID id);
}
