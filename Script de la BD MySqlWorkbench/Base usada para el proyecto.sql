use bxrwabtu14qddifcwky1
CREATE TABLE roles (
    id INT PRIMARY KEY,
    nombre VARCHAR(50)
);

-- Crear tabla clientes
CREATE TABLE clientes (
    idcliente VARCHAR(7) PRIMARY KEY,
    nom_usuario CHAR(30),
    contra_usuario VARCHAR(30),
    correo VARCHAR(30),
    telf_usuario INTEGER,
    Año_nacimiento CHAR(10),
    num_tarj INTEGER REFERENCES forma_pago(num_tarj) ON DELETE CASCADE,
    id_rol INT REFERENCES roles(id) ON DELETE CASCADE
);
ALTER TABLE cliente
ADD COLUMN saldo INT;

-- Crear tabla sala


-- Crear tabla peliculas
CREATE TABLE peliculas (
    id_pelicula INT AUTO_INCREMENT PRIMARY KEY,
    nombre_pelicula CHAR(30),
    genero CHAR(30),
    sinopsis TEXT,
    Director CHAR(30),
    anho CHAR(4),
    clasificacion VARCHAR(10),
    foto_pelicula LONGBLOB
);


CREATE TABLE Facturas (
    id_factura INT AUTO_INCREMENT PRIMARY KEY,
    nom_pelicula CHAR(30),
    nom_usuario CHAR(30),
    horario CHAR(30),
    asientos CHAR(30),
    costo_boleto INT(5)
);
CREATE TABLE sala (
    ID_Sala INT AUTO_INCREMENT PRIMARY KEY,
    Num_Sala CHAR(5),
    Horario_Sala TIME,
    Dia VARCHAR(20),
    Asientos_Disponibles INT DEFAULT 72,
    Asientos_Reservados INT,
    id_pelicula INT,
	FOREIGN KEY (id_pelicula) REFERENCES peliculas(id_pelicula) ON DELETE CASCADE
);

-- Insertar datos en la tabla roles
INSERT INTO roles (id, nombre) VALUES (1, 'usuario');
INSERT INTO roles (id, nombre) VALUES (2, 'administrador');

-- Insertar datos en la tabla clientes
INSERT INTO clientes (idcliente, nom_usuario, contra_usuario, correo, telf_usuario, Año_nacimiento, num_tarj, id_rol)
VALUES ('cli1', 'Test', 'Uno', 'usuario1@example.com', 123456789, '1990', 1712323813, 1);

INSERT INTO clientes (idcliente, nom_usuario, contra_usuario, correo, telf_usuario, Año_nacimiento, num_tarj, id_rol)
VALUES ('cli2', 'ad', 'ad', 'admin1@example.com', 987654321, '1995', 1711292192, 2);


select * from peliculas;
select * from clientes;
select * from Facturas;
