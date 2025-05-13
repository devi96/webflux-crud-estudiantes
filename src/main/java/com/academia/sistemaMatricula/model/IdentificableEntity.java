package com.academia.sistemaMatricula.model;

import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

public class IdentificableEntity<ID> implements Identificable<ID>{
    @Id
    @EqualsAndHashCode.Include
    private ID id;

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }
}
