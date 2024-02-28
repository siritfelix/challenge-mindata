# Prueba Tecnica V1

Reto por Felix Antonio Sirit Murillo siritfelix@gmail.com

Tecnologías necesarias

`Java` `Maven` `Spring-Boot` `h2` `flyway` `spring-security` `JPA`

Instalación del proyecto

Pasos para levantar la aplicacion:

1) Ejecutar el comando: 
> mvn clean package
2) En local se puede levantar con: 
>mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=local

Para dar solucion al examen, se desarrollo con las siguientes caracteristicas:
1) En el desarrollo se aplico arquitectura hexagonal, compuesto por la capa de: application, domain, infrastructure y shared.
2) Se agrego flywar, para la gestion y versionado de la db.
3) Se agregaron test Unitarios, y test de integracion, lavantando una base de datos en memoria y precardado los datos con flyway
4) La convertura de test fue de 90%, el reporte generado se encuenta en **/target/site/jacoco/index.html, para generarlo
> mvn verify
5) Se agrego documentacion de la api, la cual esta disponible al levantar el servicios en http://localhost:8080/swagger-ui/index.html
6) Se creo archivo Dockerfile para levantar la aplicacion
> build -t mindata:0.0.1 .
>
> docker run -d -p 8080:8080 --name mi_contenedor mindata:0.0.1
7) Importar las colecciones de postman /postman/**

