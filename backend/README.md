# Backend del proyecto de integración

## Teconologías usadas

- Java 17
- Spring Boot
- Maven
- MySQL 8
- Docker & Docker Compose


## Requisitos previos

- Tener instalado docker
- Una IDE compatible (en nuestro caso usamos Spring Tools Suite)
- Instala el JDK 17+

## Pasos para levantar el proyecto

### Paso 1: Levantar la base de datos con Docker

Este proyecto tiene un docker-compose.yml para levantar automáticamente, que deberá tener este contenido:

```

# Levantaremos dos servicios: MySQL 8 y phpMyAdmin

version: '3.7'

services:
  mysql:
    image: mysql:8
    container_name: 'AED-MySQL'
    environment:
      MYSQL_ROOT_PASSWORD: mysql
      MYSQL_DATABASE: sakila
      MYSQL_USER: usuarioexterno
      MYSQL_PASSWORD: Toor.toor1
    volumes:
      - mysql_data:/var/lib/mysql
      - "./sql/sakila-schema.sql:/docker-entrypoint-initdb.d/1.sql"
      - "./sql/sakila-data.sql:/docker-entrypoint-initdb.d/2.sql"
    ports:
      - 3306:3306

  phpmyadmin:
    image: phpmyadmin
    container_name: 'AED_phpMyAdmin'
    ports:
      - 8080:80
    environment:
      - PMA_HOST=mysql
    depends_on:
      - mysql

volumes:
  mysql_data:

```

Para ejecutar el contenedor tienes que estar en la raíz del proyecto y ejecutar el siguiente comando

'docker-compose up -d'

También puede copiar el contenido del docker-compose crear una carpeta vacía crear el fichero 
docker-compose.yml y hacer el comando desde esa carpeta nueva que creó 

### Paso 2: Configurar y ejecutar el backend

1. Abrir el proyecto en STS o su IDE

   · Importar como proyecto maven

2. Actualizar las dependencias

   · Dandole click derecho encima del proyecto --> Maven --> Update Project

3. Ejecutar la aplicación

   · Click derecho sobre el proyecto --> Run As --> Spring Boot App
