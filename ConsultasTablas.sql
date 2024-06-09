-- Inserción en la tabla Producto
INSERT INTO Producto (ID, Nombre, DescripcionProblema) VALUES (1, 'Dispositivo A', 'Problema de sobrecalentamiento');
INSERT INTO Producto (ID, Nombre, DescripcionProblema) VALUES (2, 'Dispositivo B', 'Fallo en la conexión de red');

-- Inserción en la tabla Persona
INSERT INTO Persona (ID, Nombre, Email, Telefono) VALUES (1, 'Juan Perez', 'juan.perez@example.com', 123456789);
INSERT INTO Persona (ID, Nombre, Email, Telefono) VALUES (2, 'Maria Lopez', 'maria.lopez@example.com', 987654321);

-- Inserción en la tabla Tickets
INSERT INTO Tickets (ID, personaID, productoID, Estado, Descripcion) VALUES (1, 1, 1, 'abierto', 'El dispositivo se sobrecalienta después de 5 minutos de uso.');
INSERT INTO Tickets (ID, personaID, productoID, Estado, Descripcion) VALUES (2, 2, 2, 'cerrado', 'No se puede conectar a la red Wi-Fi.');

-- Borrado de un registro en la tabla Tickets
DELETE FROM Tickets WHERE ID = 1;

-- Borrado de un registro en la tabla Persona
DELETE FROM Persona WHERE ID = 1;

-- Borrado de un registro en la tabla Producto
DELETE FROM Producto WHERE ID = 1;

-- Consulta específica combinando las tablas Tickets, Persona y Producto
SELECT 
    t.ID AS TicketID, 
    p.Nombre AS PersonaNombre, 
    p.Email AS PersonaEmail, 
    pr.Nombre AS ProductoNombre,
    pr.DescripcionProblema AS ProductoDescripcion, 
    t.Estado, 
    t.Descripcion
FROM 
    Tickets t
JOIN 
    Persona p ON t.personaID = p.ID
JOIN 
    Producto pr ON t.productoID = pr.ID;
