# Gestión de Productos

API REST desarrollada con Spring Boot, PostgreSQL, Redis, Kafka y Docker para la gestión básica de productos.

### Arquitectura del proyecto
Este proyecto cuenta con una arquitectura en capas, lo cual mejora el mantenimiento y la escalabilidad de la aplicación.

- Capa de presentación: Donde se exponen los endpoinds de la API.
- Capa de servicio: Lógica de negocio.
- Capa persistencia: Acceso a datos



#### Decisiones Técnicas
Para el desarrollo de esta prueba se implementaron las siguientes tecnologías:
* Java 17: Por ser una versión reciente y estable.
* Spring Boot: Facilita el desarrollo y la integración de aplicaciones.
* PostgreSQL: Utilizada para la persistencia de los datos. 
* Kafka: Para la mensajeria asíncrona. Aunque no es intensivamente usado en esta prueba, se implementa como ejemplo de integración con sistemas de eventos.
* Docker: Usado para la contenerización de la aplicación y los servicios.
* JUnit & Mockito: Utilizados para la implementación de pruebas unitarias.
* MapStruct: Mapeo automatico entre entidades y DTOs.
* Lombok: Reduce el código repetitivo. Generando un código más limpio.



## Dockerfile
Este archivo contruye la imagen de la aplicación, lleva definido el openJDK sobre el cual corre la aplicación, 
definición del nombre del jar, donde se encuentra ubicado, el puerto donde se expone la app y como se ejecuta.

## Docker-compose.yml
Este archivo orquesta servicios que se implementan en un desarrollo, para esta prueba se orquestan los siguientes:

* PostgreSQL: Base de datos con volumenes persistentes.
* Zookeeper: Gestor de coordinación de kafka.
* Kafka: Broker de mensajes.
* app: La aplicación dockerizada

### Flujo de Git
Se utilizó una estrategia de ramas inspirada en Git Flow, simplificada a:
* main: Rama principal para el despliegue.
* feature/api: Rama con el desarrollo de la API.
* feature/docker: Rama con los ficheros de configuración de Docker.


Nota: _Se agrega Postman Collection para probar los endpoints_.