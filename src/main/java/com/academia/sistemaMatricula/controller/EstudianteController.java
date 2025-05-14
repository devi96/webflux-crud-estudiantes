package com.academia.sistemaMatricula.controller;

import com.academia.sistemaMatricula.dto.CursoDTO;
import com.academia.sistemaMatricula.dto.EstudianteDTO;
import com.academia.sistemaMatricula.model.Curso;
import com.academia.sistemaMatricula.model.Estudiante;
import com.academia.sistemaMatricula.service.IEstudianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final IEstudianteService service;
    private final ModelMapper modelMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<EstudianteDTO>>> findAll(@RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
        Flux<EstudianteDTO> fx = service.findAll(order)
                .map(this::convertToDto);
        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> findById(@PathVariable String id) {
        return service.findById(id)
                .map(this::convertToDto)
                .map(e-> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<EstudianteDTO>> save(@Valid @RequestBody EstudianteDTO estudiante) {
        return service.save(convertToEntity(estudiante)).map(
                e-> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(convertToDto(e)))
                .defaultIfEmpty(ResponseEntity.notFound().build()
        );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<EstudianteDTO>> update(@PathVariable String id, @Valid @RequestBody EstudianteDTO estudiante) {
        return service.update(id, convertToEntity(estudiante))
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(convertToDto(e)))
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
    private EstudianteDTO convertToDto(Estudiante estudiante) {
        return modelMapper.map(estudiante, EstudianteDTO.class);
    }
    private Estudiante convertToEntity(EstudianteDTO estudianteDTO) {
        return modelMapper.map(estudianteDTO, Estudiante.class);
    }
}

