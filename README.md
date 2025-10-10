# 📚 Bookstore API

Una API para gestionar **libros**, **clientes** y **ventas** de una librería.  
Construida con **Java**, **Spring Boot**, **Spring Data JPA** y **MySQL** como base de datos.

---

## 🐳 Ejecución con Docker Compose

Ahora puedes ejecutar toda la aplicación (API + Base de datos) fácilmente con **Docker Compose**.

---

### ⚙️ Requisitos previos

- [Docker](https://docs.docker.com/get-docker/)  
- [Docker Compose](https://docs.docker.com/compose/install/)  
- (Opcional) **Maven**, si deseas compilar manualmente antes de construir la imagen

---

### 🚀 1. Clonar el repositorio

```bash
git clone https://github.com/luuzuriaga/bookstore.git
cd bookstore

🧩 2. Estructura del proyecto

bookstore/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/bookstore/...
│   │   └── resources/application.properties
│   └── test/
│       └── java/com/bookstore/...

▶️ 3. Levantar los contenedores con Docker Compose

Ejecuta:
sudo docker compose up --build

Esto hará lo siguiente:

Construirá la imagen Docker de la API de Spring Boot.
Levantará un contenedor MySQL 8.0 con la base de datos bookstoredb.
Conectará ambos servicios en una red interna.
Expondrá los puertos:
8080 → API Bookstore
3306 → Base de datos MySQL

🌍 4. Acceder a la API

Una vez iniciados los contenedores, podrás acceder desde tu máquina local:

http://<IP_DEL_SERVIDOR_UBUNTU>:8080/api/clientes

📌 Si estás en la misma red local, reemplaza <IP_DEL_SERVIDOR_UBUNTU> por la IP real del servidor Ubuntu.

🧠 5. Comandos útiles

Ver contenedores activos:
sudo docker compose ps

Ver logs en tiempo real:
sudo docker compose logs -f

Detener y elimi
sudo docker compose down

Reiniciar solo la API:
sudo docker compose restart api

⚙️ Configuración de Docker Compose

Tu archivo docker-compose.yml se vería así:

version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: mysql-bookstore
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: admin123
      MYSQL_DATABASE: bookstoredb
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  api:
    build: .
    container_name: bookstore-api
    restart: always
    depends_on:
      - mysql
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/bookstoredb
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: admin123
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
      SPRING_JPA_SHOW_SQL: "true"
      SPRING_JPA_DATABASE_PLATFORM: org.hibernate.dialect.MySQLDialect
      SERVER_ADDRESS: 0.0.0.0
    ports:
      - "8080:8080"

volumes:
  mysql_data:

🧱 Ejecución manual (opcional, sin Docker Compose)

También puedes ejecutar los contenedores manualmente:

# Base de datos MySQL
docker run -d --name mysql-bookstore \
  -e MYSQL_ROOT_PASSWORD=admin123 \
  -e MYSQL_DATABASE=bookstoredb \
  -p 3306:3306 mysql:8.0

# API de Spring Boot
mvn clean package -DskipTests
docker build -t bookstore-api .

docker run -d --name bookstore-api \
  --link mysql-bookstore:mysql \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/bookstoredb \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=admin123 \
  bookstore-api

🧪 Tecnologías utilizadas

Java 21

Spring Boot 3.5+
Spring Data JPA
MySQL 8
Maven
JUnit + Mockito
Docker / Docker Compose

🛠 Endpoints principales
| Método | URL               | Descripción             |
| ------ | ----------------- | ----------------------- |
| GET    | `/api/books`      | Listar todos los libros |
| GET    | `/api/books/{id}` | Obtener libro por ID    |
| POST   | `/api/books`      | Crear un libro          |
| DELETE | `/api/books/{id}` | Eliminar libro por ID   |

Ejemplo POST:

{
  "title": "Good Omens",
  "author": "Neil Gaiman, Terry Pratchett",
  "price": 45,
  "stock": 5
}

Crear cliente

{
  "nombre": "Juan",
  "email": "juan@mail.com"
}

Listar clientes
GET http://<IP_DEL_SERVIDOR>:8080/api/clientes

Obtener cliente por ID
GET http://<IP_DEL_SERVIDOR>:8080/api/clientes/1

Eliminar cliente
DELETE http://<IP_DEL_SERVIDOR>:8080/api/clientes/1


📚 Libros

Crear libro
POST http://<IP_DEL_SERVIDOR>:8080/api/books
Content-Type: application/json

{
  "title": "El nombre del viento",
  "author": "Patrick Rothfuss",
  "price": 39.99,
  "stock": 12
}

Listar libros
GET http://<IP_DEL_SERVIDOR>:8080/api/books

💸 Ventas

Registrar venta
POST http://<IP_DEL_SERVIDOR>:8080/api/ventas
Content-Type: application/json

{
  "clienteId": 1,
  "libroId": 1,
  "cantidad": 2
}

Listar ventas
GET http://<IP_DEL_SERVIDOR>:8080/api/ventas

🧰 Ejecución sin Docker (modo desarrollo)
# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run

Por defecto, usará MySQL configurado en src/main/resources/application.properties.

API desarrollada con ❤️ usando Spring Boot y contenedorizada con Docker Compose.