-- ============================================================
-- Script 3: pruebas-transacciones.sql
-- Proposito: Verificar que las transacciones de base de datos
-- son atomicas (todo o nada) y que el ROLLBACK funciona.
-- Proyecto: SAFAB - AppEducativa
-- ============================================================

USE empresa_db;

-- ============================================================
-- Paso 1: Prueba de transaccion exitosa (COMMIT)
-- ============================================================

-- Contar registros ANTES
SELECT COUNT(*) AS total_preguntas_antes FROM preguntas;
SELECT COUNT(*) AS total_tipos_antes FROM tipos_preguntas;

-- Iniciar la transaccion
START TRANSACTION;

    -- Insertar un tipo de pregunta de prueba
    INSERT INTO tipos_preguntas (nombre, Descripcion)
    VALUES ('TEST_COMMIT', 'Tipo insertado en transaccion de prueba COMMIT');

    -- Insertar una pregunta asociada al tipo recien creado
    INSERT INTO preguntas (Enunciado, tipo_pregunta_id)
    VALUES ('PRUEBA_COMMIT: ¿Que verifica una transaccion COMMIT?', LAST_INSERT_ID());

    -- Verificar que los registros existen DENTRO de la transaccion
    SELECT * FROM tipos_preguntas WHERE nombre = 'TEST_COMMIT';
    SELECT * FROM preguntas WHERE Enunciado LIKE 'PRUEBA_COMMIT%';

-- Confirmar la transaccion
COMMIT;

-- Contar registros DESPUES del COMMIT
SELECT COUNT(*) AS total_preguntas_despues FROM preguntas;
SELECT COUNT(*) AS total_tipos_despues FROM tipos_preguntas;

-- Verificar que los registros quedaron guardados
SELECT * FROM tipos_preguntas WHERE nombre = 'TEST_COMMIT';
SELECT * FROM preguntas WHERE Enunciado LIKE 'PRUEBA_COMMIT%';
-- Resultado esperado: 1 fila en cada consulta
-- total_despues debe ser total_antes + 1 en cada tabla

-- ============================================================
-- Paso 2: Prueba de transaccion revertida (ROLLBACK)
-- ============================================================

-- Contar registros ANTES del ROLLBACK
SELECT COUNT(*) AS total_preguntas_antes_rollback FROM preguntas;
SELECT COUNT(*) AS total_tipos_antes_rollback FROM tipos_preguntas;

-- Iniciar la transaccion
START TRANSACTION;

    -- Insertar un tipo de pregunta de prueba
    INSERT INTO tipos_preguntas (nombre, Descripcion)
    VALUES ('TEST_ROLLBACK', 'Tipo insertado en transaccion que sera revertida');

    -- Insertar una pregunta asociada
    INSERT INTO preguntas (Enunciado, tipo_pregunta_id)
    VALUES ('PRUEBA_ROLLBACK: Este registro NO debe quedar guardado', LAST_INSERT_ID());

    -- Verificar que los registros existen DENTRO de la transaccion
    SELECT * FROM tipos_preguntas WHERE nombre = 'TEST_ROLLBACK';
    SELECT * FROM preguntas WHERE Enunciado LIKE 'PRUEBA_ROLLBACK%';
    -- Dentro de la transaccion: se ven los registros

-- Revertir la transaccion (simula un error del sistema)
ROLLBACK;

-- Contar registros DESPUES del ROLLBACK
SELECT COUNT(*) AS total_preguntas_despues_rollback FROM preguntas;
SELECT COUNT(*) AS total_tipos_despues_rollback FROM tipos_preguntas;

-- Verificar que los registros NO quedaron guardados
SELECT * FROM tipos_preguntas WHERE nombre = 'TEST_ROLLBACK';
SELECT * FROM preguntas WHERE Enunciado LIKE 'PRUEBA_ROLLBACK%';
-- Resultado esperado: 0 filas en cada consulta
-- total_despues debe ser igual a total_antes

-- ============================================================
-- Paso 3: Limpiar datos de prueba al finalizar
-- (Ejecutar SOLO despues de tomar todas las evidencias)
-- ============================================================

DELETE FROM preguntas 
WHERE Enunciado LIKE 'PRUEBA_COMMIT%' 
   OR Enunciado LIKE 'PRUEBA_001%'
   OR Enunciado LIKE 'PRUEBA_002%'
   OR Enunciado LIKE 'PRUEBA_003%';

DELETE FROM tipos_preguntas 
WHERE nombre IN ('TEST_COMMIT', 'TEST_TipoPrueba1', 'TEST_TipoPrueba2');

-- Verificar que la limpieza fue exitosa
SELECT COUNT(*) AS datos_prueba_restantes 
FROM preguntas 
WHERE Enunciado LIKE 'PRUEBA_%';
-- Resultado esperado: 0

SELECT COUNT(*) AS tipos_prueba_restantes
FROM tipos_preguntas
WHERE nombre LIKE 'TEST_%';
-- Resultado esperado: 0
