CREATE TABLE Producto (
  ID int PRIMARY KEY,
  Nombre VARCHAR(255),
  DescripcionProblema VARCHAR(255)
);

CREATE TABLE Persona (
  ID int PRIMARY KEY,
  Nombre VARCHAR(255),
  Email VARCHAR(255),
  Telefono int
);

CREATE TABLE Tickets (
  ID int PRIMARY KEY,
  personaID int,
  productoID int,
  mensaje VARCHAR(50),
  Descripcion TEXT,
  FOREIGN KEY (personaID) REFERENCES Persona(ID),
  FOREIGN KEY (productoID) REFERENCES Producto(ID)
);
