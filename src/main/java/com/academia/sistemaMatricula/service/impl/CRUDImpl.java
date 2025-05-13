package com.academia.sistemaMatricula.service.impl;

import com.academia.sistemaMatricula.model.IdentificableEntity;
import com.academia.sistemaMatricula.repository.IGenericRepo;
import com.academia.sistemaMatricula.service.ICRUD;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID>{

    protected abstract IGenericRepo<T, ID> getRepo();
    protected abstract void applyUpdates(T existing, T updated);

    @Override
    public Mono<T> save(T t) {
        return getRepo().save(t);
    }

    @Override
    public Mono<T> update(ID id, T t) {
        //validar ID
        return getRepo().findById(id)
                .flatMap( exist -> {
                    applyUpdates(exist, t);
                    return getRepo().save(exist);
                });
    }

    @Override
    public Flux<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public Mono<T> findById(ID id) {
        return getRepo().findById(id);
    }

    @Override
    public Mono<Boolean> delete(ID id) {
        return getRepo().findById(id)
                .flatMap(e -> getRepo().delete(e).thenReturn(true))
                .defaultIfEmpty(false);
    }
}
