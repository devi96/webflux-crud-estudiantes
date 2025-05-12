package com.academia.sistemaMatricula.service.impl;

import com.academia.sistemaMatricula.model.Estudiante;
import com.academia.sistemaMatricula.repository.IEstudianteRepo;
import com.academia.sistemaMatricula.repository.IGenericRepo;
import com.academia.sistemaMatricula.service.IEstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl extends CRUDImpl<Estudiante, String> implements IEstudianteService {

    private final IEstudianteRepo repo;

    @Override
    protected IGenericRepo<Estudiante, String> getRepo(){
     return repo;
    }
}
