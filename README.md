# 📚 Bookstore API

Una API para gestionar **libros**, **clientes** y **ventas** de una librería.  
Construida con **Java**, **Spring Boot**, **Spring Data JPA** y **H2/MySQL** como base de datos.

---

## 🚀 Ejecutar la aplicación

1. Clonar el repositorio:
```bash
git clone https://github.com/luuzuriaga/bookstore.git

cd bookstore

bookstore/
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── bookstore
│   │   │           ├── BookstoreApplication.java
│   │   │           ├── controller
│   │   │           │   ├── BookController.java
│   │   │           │   ├── ClienteController.java
│   │   │           │   └── VentaController.java
│   │   │           ├── model
│   │   │           │   ├── Book.java
│   │   │           │   ├── Cliente.java
│   │   │           │   └── Venta.java
│   │   │           ├── repository
│   │   │           │   ├── BookRepository.java
│   │   │           │   ├── ClienteRepository.java
│   │   │           │   └── VentaRepository.java
│   │   │           └── service
│   │   │               ├── BookService.java
│   │   │               ├── ClienteService.java
│   │   │               └── VentaService.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── data.sql (opcional, para insertar datos iniciales)
│   └── test
│       └── java
│           └── com
│               └── bookstore
│                   └── BookstoreApplicationTests.java



---

---

## 🚀 Tecnologías

- Java 17+
- Spring Boot 3+
- Spring Data JPA
- H2 Database / MySQL (configurable)
- Maven
- JUnit + Mockito (tests)

---

## 🛠 Endpoints principales

### Libros

| Método | URL             | Descripción              |
|--------|-----------------|-------------------------|
| GET    | `/api/books`    | Listar todos los libros |
| GET    | `/api/books/{id}` | Obtener libro por ID |
| POST   | `/api/books`    | Crear un libro          |
| DELETE | `/api/books/{id}` | Eliminar libro por ID |

**Ejemplo POST**:

```json
{
  "title": "Good Omens",
  "author": "Neil Gaiman, Terry Pratchett",
  "price": 45,
  "stock": 5
}

⚙️ Cómo ejecutar
1.	Clona el repositorio:

git clone https://github.com/luuzuriaga/bookstore.git
cd bookstore

2.	Construir el proyecto:
mvn clean install

3.	Ejecutar la aplicación:
mvn spring-boot:run

1`  4.	Probar los endpoints usando Postman o similar.

