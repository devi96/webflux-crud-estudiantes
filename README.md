
# Webflux CRUD Estudiantes

Este proyecto implementa un CRUD de estudiantes utilizando Spring WebFlux y MongoDB.

## Estructura del Proyecto

- **Colección de Postman:**  
  La colección para probar los endpoints de la API se encuentra en:  
  `./SistemaMatricula.postman_collection.json`

- **Docker Compose:**  
  El archivo para levantar los servicios necesarios (incluyendo MongoDB) está en:  
  `./docker-compose.yml`

- **Script de Inicialización de MongoDB:**  
  El script para inicializar la base de datos MongoDB se encuentra en(Solo estan los usuarios y roles):  
  `./mongo-init/mongo-init.js`

## Uso

1. Levanta los servicios con Docker Compose:
   ```bash
   docker-compose up -d
   ```

2. Importa la colección de Postman desde `./SistemaMatricula.postman_collection.json` para probar los endpoints.

3. El script `mongo-init.js` se ejecuta automáticamente al iniciar el contenedor de MongoDB.

## Requisitos

- Docker y Docker Compose
- Postman (opcional, para pruebas manuales)


## Notas
- Las rutas para la version con @RestController
    - localhost:8080/cursos/6824256c7f61721b7d2c7c7b
- Las rutas para la version con functional endpoints solo llevan un v2:
    - localhost:8080/v2/cursos/6824256c7f61721b7d2c7c7b
- Por defecto el findAll de estudiantes hace un sort por edad en forma asc, si se le agrega desc lo hara de manera ascendente
    - /estudiantes?order=desc