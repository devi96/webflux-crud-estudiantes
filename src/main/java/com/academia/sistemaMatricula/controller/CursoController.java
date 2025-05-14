package com.academia.sistemaMatricula.controller;

import com.academia.sistemaMatricula.dto.CursoDTO;
import com.academia.sistemaMatricula.model.Curso;
import com.academia.sistemaMatricula.service.ICursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final ICursoService service;
    private final ModelMapper modelMapper;

    @GetMapping("{id}")
    public Mono<ResponseEntity<CursoDTO>> findById(@PathVariable String id){
        return service.findById(id)
                .map(e -> convertToDto(e))
                .map( e -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping
    public Mono<ResponseEntity<Flux<CursoDTO>>> findAll() {
        Flux<CursoDTO> fx = service.findAll()
                .map(this::convertToDto);
        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<CursoDTO>> save(@Valid @RequestBody CursoDTO cursoDTO){
        return service.save(this.convertToEntity(cursoDTO))
                .map(this::convertToDto)
                .map( e -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e));
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<CursoDTO>> update(@PathVariable String id, @Valid @RequestBody CursoDTO cursoDTO) {
        return service.update(id, this.convertToEntity(cursoDTO))
                .map(this::convertToDto)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
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

    private CursoDTO convertToDto(Curso curso) {
        return modelMapper.map(curso, CursoDTO.class);
    }
    private Curso convertToEntity(CursoDTO cursoDTO) {
        return modelMapper.map(cursoDTO, Curso.class);
    }

}
