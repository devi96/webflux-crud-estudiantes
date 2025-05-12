package com.academia.sistemaMatricula.service.impl;

import com.academia.sistemaMatricula.model.Curso;
import com.academia.sistemaMatricula.repository.ICursoRepo;
import com.academia.sistemaMatricula.repository.IGenericRepo;
import com.academia.sistemaMatricula.service.ICursoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl extends CRUDImpl<Curso, String> implements ICursoService{

    private final ICursoRepo repo;

    @Override
    protected IGenericRepo<Curso, String> getRepo() {
        return repo;
    }
}
