-- ============================================================
-- Script 2: pruebas-integridad.sql
-- Proposito: Verificar que la base de datos no tiene 
-- inconsistencias despues de las operaciones del sistema.
-- Proyecto: SAFAB - AppEducativa
-- ============================================================

USE empresa_db;

-- ============================================================
-- Paso 1: Verificar integridad referencial
-- ============================================================

-- Buscar preguntas sin tipo de pregunta valido (registros huerfanos)
SELECT p.*
FROM preguntas p
LEFT JOIN tipos_preguntas tp ON p.tipo_pregunta_id = tp.Id
WHERE tp.Id IS NULL;
-- Resultado esperado: 0 filas

-- Buscar roles sin usuario valido (registros huerfanos)
SELECT r.*
FROM roles r
LEFT JOIN usuarios u ON r.usuario_id = u.id
WHERE u.id IS NULL;
-- Resultado esperado: 0 filas

-- ============================================================
-- Paso 2: Verificar unicidad
-- ============================================================

-- Detectar nombres de usuario duplicados
SELECT nombre, COUNT(*) AS cantidad
FROM usuarios
GROUP BY nombre
HAVING COUNT(*) > 1;
-- Resultado esperado: 0 filas

-- Detectar nombres de tipo pregunta duplicados
SELECT nombre, COUNT(*) AS cantidad
FROM tipos_preguntas
GROUP BY nombre
HAVING COUNT(*) > 1;
-- Resultado esperado: 0 filas

-- ============================================================
-- Paso 3: Verificar campos obligatorios
-- ============================================================

-- Usuarios sin nombre
SELECT * FROM usuarios
WHERE nombre IS NULL OR nombre = '';
-- Resultado esperado: 0 filas

-- Usuarios sin clave
SELECT id, nombre, activo FROM usuarios
WHERE clave IS NULL OR clave = '';
-- Resultado esperado: 0 filas

-- Preguntas sin enunciado
SELECT * FROM preguntas
WHERE Enunciado IS NULL OR Enunciado = '';
-- Resultado esperado: 0 filas

-- Preguntas sin tipo de pregunta asignado
SELECT * FROM preguntas
WHERE tipo_pregunta_id IS NULL;
-- Resultado esperado: 0 filas

-- Tipos de pregunta sin nombre
SELECT * FROM tipos_preguntas
WHERE nombre IS NULL OR nombre = '';
-- Resultado esperado: 0 filas

-- ============================================================
-- Paso 4: Verificar consistencia de roles
-- ============================================================

-- Roles con nombre invalido (distinto de ADMIN o USER)
SELECT * FROM roles
WHERE nombre NOT IN ('ADMIN', 'USER');
-- Resultado esperado: 0 filas (ajustar segun roles validos del sistema)

-- Usuarios activos sin rol asignado
SELECT u.id, u.nombre, u.activo
FROM usuarios u
LEFT JOIN roles r ON r.usuario_id = u.id
WHERE r.id IS NULL AND u.activo = 1;
-- Resultado esperado: 0 filas

-- ============================================================
-- Paso 5: Resumen de integridad
-- ============================================================

SELECT 'Usuarios totales' AS concepto, COUNT(*) AS cantidad FROM usuarios
UNION ALL
SELECT 'Usuarios activos', COUNT(*) FROM usuarios WHERE activo = 1
UNION ALL
SELECT 'Usuarios inactivos', COUNT(*) FROM usuarios WHERE activo = 0
UNION ALL
SELECT 'Roles totales', COUNT(*) FROM roles
UNION ALL
SELECT 'Tipos de pregunta totales', COUNT(*) FROM tipos_preguntas
UNION ALL
SELECT 'Preguntas totales', COUNT(*) FROM preguntas;

-- Tomar captura de pantalla de este resultado.
-- Es la evidencia del estado final de la base de datos.
