package com.academia.sistemaMatricula.repository;

import com.academia.sistemaMatricula.model.Curso;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ICursoRepo extends IGenericRepo<Curso, String> {
}
