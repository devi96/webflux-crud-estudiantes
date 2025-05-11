package com.academia.sistemaMatricula.repository;

import com.academia.sistemaMatricula.model.Matricula;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IMatriculaRepo extends IGenericRepo<Matricula, String> {
}
