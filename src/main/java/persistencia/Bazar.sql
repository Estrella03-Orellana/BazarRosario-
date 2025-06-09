CREATE DATABASE BD_Bazar
USE BD_Bazar 
Go

-- Crear la tabla dbo.FACTURAS
CREATE TABLE FACTURAS (
    CodFac INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
    NombreUsu NVARCHAR(80) NOT NULL,
    Pago DECIMAL(18, 2) NOT NULL,
    Fecha DATETIME2(7) NOT NULL
);

-- Crear la tabla Categorias
CREATE TABLE CATEGORIAS (
    CatId INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
    Nombre NVARCHAR(150) NOT NULL,
);


-- Crear la tabla PRODUCTOS
CREATE TABLE PRODUCTOS (
    ProdId INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
    CategoriaId INT NOT NULL,
    TipoProdu NVARCHAR(50) NOT NULL,
    NombreProdu NVARCHAR(250) NOT NULL,
    Precio DECIMAL(18, 2) NOT NULL,
    Stock INT NOT NULL,
    IsOferta BIT NOT NULL
    CONSTRAINT PRODUCTOS_CATEGORIAS FOREIGN KEY (CategoriaId)
        REFERENCES CATEGORIAS(CatId)
);


-- Crear la tabla USUARIOS
CREATE TABLE USUARIOS (
    Usuld INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
    NombreUsu NVARCHAR(80) NOT NULL,
    Email NVARCHAR(90) UNIQUE NOT NULL,
    Password NVARCHAR(64) NOT NULL,
    IsAdmin BIT NOT NULL
);


-- Crear la tabla FACTURAS_DETALLE
CREATE TABLE FACTURAS_DETALLE (
    Id INT PRIMARY KEY IDENTITY(1,1) NOT NULL,
    CodFac INT NOT NULL,
    NombreUsu NVARCHAR(80) NOT NULL,
    NombreProdu NVARCHAR(250) NOT NULL,
    Cantidad INT NOT NULL,
    Pago DECIMAL(18, 2) NOT NULL,
    CONSTRAINT FACTURAS_DETALLE_FACTURAS FOREIGN KEY (CodFac)
        REFERENCES FACTURAS(CodFac)
);

--Insertando datos
INSERT INTO CATEGORIAS(Nombre) VALUES('Electrodomesticos'),
('Tecnologia'),
('Juguetes'),
('Limpieza'),
('Ropa'),
('Confiteria y Papeleria'),
('Deportes'),
('Hogar y Cocina'),
('Ofertas'),
('Cuidado Personal');
