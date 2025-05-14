package com.academia.sistemaMatricula.handler;

import com.academia.sistemaMatricula.dto.MatriculaDTO;
import com.academia.sistemaMatricula.model.Matricula;
import com.academia.sistemaMatricula.service.IMatriculaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
@Component
@RequiredArgsConstructor
public class MatriculaHandler {
    private final IMatriculaService service;
    private final ModelMapper modelMapper;

    public Mono<ServerResponse> findById(ServerRequest request){
        String id = request.pathVariable("id");

        return service.findById(id)
                .map(this::convertToDto)
                .flatMap( e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }
    @GetMapping
    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<MatriculaDTO> fx = service.findAll()
                .map(this::convertToDto);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx,MatriculaDTO.class);
    }

    @PostMapping
    public Mono<ServerResponse> save(ServerRequest request){
        Mono<MatriculaDTO> monoMatriculaDTO = request.bodyToMono(MatriculaDTO.class);
        return monoMatriculaDTO
                .map(this::convertToEntity)
                .flatMap(service::save)
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<MatriculaDTO> monoMatriculaDTO = request.bodyToMono(MatriculaDTO.class);

        return monoMatriculaDTO
                .map(this::convertToEntity)
                .flatMap(Matricula -> service.update(id, Matricula))
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return service.delete(id)
                .flatMap(result -> {
                    if(result){
                        return ServerResponse.noContent().build();
                    } else {
                        return ServerResponse.notFound().build();
                    }
                });
    }

    private MatriculaDTO convertToDto(Matricula Matricula) {
        return modelMapper.map(Matricula, MatriculaDTO.class);
    }
    private Matricula convertToEntity(MatriculaDTO MatriculaDTO) {
        return modelMapper.map(MatriculaDTO, Matricula.class);
    }
}
