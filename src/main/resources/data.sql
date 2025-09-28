-- Datos iniciales para desarrollo y testing

-- Insertar libros de ejemplo
INSERT INTO books (title, author, price, stock) VALUES
('Harry Potter', 'J.K. Rowling', 50.00, 10),
('El Señor de los Anillos', 'J.R.R. Tolkien', 40.00, 5),
('Clean Code', 'Robert C. Martin', 39.99, 8),
('The Pragmatic Programmer', 'David Thomas', 44.99, 12),
('Design Patterns', 'Gang of Four', 54.99, 7);

-- Insertar clientes de ejemplo
INSERT INTO clientes (nombre, email) VALUES
('Juan Pérez', 'juan@example.com'),
('María García', 'maria@example.com'),
('Carlos López', 'carlos@example.com');