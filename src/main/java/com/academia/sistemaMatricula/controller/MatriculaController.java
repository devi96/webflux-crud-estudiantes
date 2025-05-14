package com.academia.sistemaMatricula.controller;

import com.academia.sistemaMatricula.dto.MatriculaDTO;
import com.academia.sistemaMatricula.model.Matricula;
import com.academia.sistemaMatricula.service.IMatriculaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final IMatriculaService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<MatriculaDTO>>> findAll() {
        Flux<MatriculaDTO> fx = service.findAll()
                .map(this::convertToDto);
        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx));
    }
    //Metodo para matricularse
    @PostMapping("/registrar")
    public Mono<ResponseEntity<MatriculaDTO>> registrarMatricula(@Valid @RequestBody MatriculaDTO matriculaDTO) {
        return service.registrarMatricula(convertToEntity(matriculaDTO))
                .map(matricula -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(convertToDto(matricula)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<MatriculaDTO>> findById(@PathVariable String id) {
        return service.findById(id)
                .map(matricula -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(convertToDto(matricula)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<MatriculaDTO>> update(@PathVariable String id,@Valid @RequestBody MatriculaDTO matriculaDTO) {
        return service.update(id, convertToEntity(matriculaDTO))
                .map(matricula -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(convertToDto(matricula)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return service.delete(id)
                .flatMap(result -> {
                    if(result){
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }
    private MatriculaDTO convertToDto(Matricula matricula) {
        return modelMapper.map(matricula, MatriculaDTO.class);
    }
    private Matricula convertToEntity(MatriculaDTO matriculaDTO) {
        return modelMapper.map(matriculaDTO, Matricula.class);
    }

}
