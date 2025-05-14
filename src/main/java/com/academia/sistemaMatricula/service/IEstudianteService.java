package com.academia.sistemaMatricula.service;

import com.academia.sistemaMatricula.model.Estudiante;
import reactor.core.publisher.Flux;

public interface IEstudianteService extends ICRUD<Estudiante, String> {
    Flux<Estudiante> findAll(String order);
}
