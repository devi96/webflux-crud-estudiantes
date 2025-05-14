package com.academia.sistemaMatricula.service;


import com.academia.sistemaMatricula.model.Matricula;
import reactor.core.publisher.Mono;

public interface IMatriculaService extends ICRUD<Matricula, String> {
    Mono<Matricula> registrarMatricula(Matricula matricula);
}