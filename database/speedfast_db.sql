-- =============================================================
-- SpeedFast - Semana 7: Script de base de datos (MySQL 8.4)
-- Ejecutar completo en MySQL Workbench o por consola.
-- =============================================================

CREATE DATABASE IF NOT EXISTS speedfast_db;
USE speedfast_db;

-- Tabla repartidor: un repartidor puede realizar muchas entregas
CREATE TABLE IF NOT EXISTS repartidor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla pedido: un pedido puede tener una o varias entregas
CREATE TABLE IF NOT EXISTS pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    direccion VARCHAR(150) NOT NULL,
    tipo VARCHAR(30) NOT NULL,      -- COMIDA | ENCOMIENDA | EXPRESS
    estado VARCHAR(20) NOT NULL     -- PENDIENTE | EN_REPARTO | ENTREGADO
);

-- Tabla entrega: cada entrega se asocia a un pedido y a un repartidor
CREATE TABLE IF NOT EXISTS entrega (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_repartidor INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedido(id),
    FOREIGN KEY (id_repartidor) REFERENCES repartidor(id)
);

-- -------------------------------------------------------------
-- Datos de prueba
-- -------------------------------------------------------------
INSERT INTO repartidor (nombre) VALUES
('Ana Soto'), ('Juan Perez'), ('Melisa Rojas');

INSERT INTO pedido (direccion, tipo, estado) VALUES
('Av. Providencia 123', 'COMIDA', 'PENDIENTE'),
('Los Leones 456', 'EXPRESS', 'PENDIENTE'),
('Apoquindo 3000', 'ENCOMIENDA', 'PENDIENTE');

INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES
(1, 1, CURDATE(), CURTIME());

-- -------------------------------------------------------------
-- Verificacion de relaciones y restricciones
-- -------------------------------------------------------------
-- SHOW CREATE TABLE entrega;
-- La siguiente sentencia DEBE fallar (Error 1452: el pedido 99 no existe):
-- INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (99, 1, CURDATE(), CURTIME());
