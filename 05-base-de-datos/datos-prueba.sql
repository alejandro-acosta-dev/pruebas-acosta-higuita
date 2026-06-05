-- ============================================================
-- Script 1: datos-prueba.sql
-- Proposito: Insertar datos controlados para garantizar que
-- los casos de prueba tengan datos conocidos y reproducibles.
-- Proyecto: SAFAB - AppEducativa
-- Tablas: usuarios, roles, tipos_preguntas, preguntas
-- ============================================================

-- Paso 1: Seleccionar la base de datos
USE empresa_db;

-- Paso 2: Verificar estructura de las tablas
SHOW TABLES;
DESCRIBE usuarios;
DESCRIBE roles;
DESCRIBE tipos_preguntas;
DESCRIBE preguntas;

-- Paso 3: Limpiar datos de prueba anteriores (solo ambiente de pruebas)
DELETE FROM preguntas WHERE Enunciado LIKE 'PRUEBA_%';
DELETE FROM roles WHERE nombre = 'TEST_ROL';
DELETE FROM usuarios WHERE nombre LIKE 'test_%';
DELETE FROM tipos_preguntas WHERE nombre LIKE 'TEST_%';

-- Verificar limpieza
SELECT 'Datos de prueba anteriores eliminados' AS estado;

-- Paso 4: Insertar tipos de pregunta de prueba
INSERT INTO tipos_preguntas (nombre, Descripcion)
VALUES 
    ('TEST_TipoPrueba1', 'Tipo de pregunta insertado para pruebas automatizadas'),
    ('TEST_TipoPrueba2', 'Segundo tipo de prueba para validaciones');

-- Verificar insercion de tipos
SELECT * FROM tipos_preguntas WHERE nombre LIKE 'TEST_%';
-- Resultado esperado: 2 filas

-- Paso 5: Insertar preguntas de prueba
-- Nota: tipo_pregunta_id debe corresponder a IDs existentes en tipos_preguntas
INSERT INTO preguntas (Enunciado, tipo_pregunta_id)
VALUES
    ('PRUEBA_001: ¿Que es una variable en programacion?', 1),
    ('PRUEBA_002: ¿Que es un bucle for?', 1),
    ('PRUEBA_003: ¿Verdadero o falso: Java es interpretado?', 2);

-- Verificar insercion de preguntas
SELECT * FROM preguntas WHERE Enunciado LIKE 'PRUEBA_%';
-- Resultado esperado: 3 filas

-- Paso 6: Resumen final de datos insertados
SELECT 'usuarios' AS tabla, COUNT(*) AS total FROM usuarios
UNION ALL
SELECT 'roles', COUNT(*) FROM roles
UNION ALL
SELECT 'tipos_preguntas', COUNT(*) FROM tipos_preguntas
UNION ALL
SELECT 'preguntas', COUNT(*) FROM preguntas;
