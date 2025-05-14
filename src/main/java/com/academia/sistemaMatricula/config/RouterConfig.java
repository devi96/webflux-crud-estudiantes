package com.academia.sistemaMatricula.config;

import com.academia.sistemaMatricula.handler.CursoHandler;
import com.academia.sistemaMatricula.handler.EstudianteHandler;
import com.academia.sistemaMatricula.handler.MatriculaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> CursoRoutes(CursoHandler handler) {//, ClientHandler clientHandler) {
        return route(GET("/v2/cursos"), handler::findAll) //req -> handler.findAll(req)
                .andRoute(GET("/v2/cursos/{id}"), handler::findById)
                .andRoute(POST("/v2/cursos"), handler::save)
                .andRoute(PUT("/v2/cursos/{id}"), handler::update)
                .andRoute(DELETE("/v2/cursos/{id}"), handler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> clientRoutes(EstudianteHandler handler) {
        return route(GET("/v2/estudiantes"), handler::findAll)
                .andRoute(GET("/v2/estudiantes/{id}"), handler::findById)
                .andRoute(POST("/v2/estudiantes"), handler::save)
                .andRoute(PUT("/v2/estudiantes/{id}"), handler::update)
                .andRoute(DELETE("/v2/estudiantes/{id}"), handler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> invoiceRoutes(MatriculaHandler handler) {
        return route(GET("/v2/matriculas"), handler::findAll)
                .andRoute(GET("/v2/matriculas/{id}"), handler::findById)
                .andRoute(POST("/v2/matriculas/registrar"), handler::registrarMatricula)
                .andRoute(PUT("/v2/matriculas/{id}"), handler::update)
                .andRoute(DELETE("/v2/matriculas/{id}"), handler::delete);
    }
}
