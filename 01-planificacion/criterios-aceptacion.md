# Criterios de Aceptación — SAFAB
**Sistema Adaptativo de Funciones en Algoritmia Básica**

**Repositorio:** `pruebas-acosta-higuita`  
**Integrantes:** Alejandro Acosta — Juan David Higuita  
**Fecha de elaboración:** Junio 2025  
**Versión:** 1.0

---

## Módulo: Login (`POST /login`)

| ID     | Criterio de aceptación |
|--------|------------------------|
| CA-001 | Dado un usuario registrado y activo con credenciales correctas, cuando envía `nombre` y `clave` al endpoint `POST /login`, entonces el sistema responde con código HTTP 200, un token JWT válido, el nombre del usuario, su ID y la lista de roles. |
| CA-002 | Dado un usuario registrado y activo, cuando envía una clave incorrecta al endpoint `POST /login`, entonces el sistema responde con código HTTP 401 y el mensaje `"credenciales inválidas"`, sin revelar qué campo es incorrecto. |
| CA-003 | Dado un nombre de usuario que no existe en la base de datos, cuando se intenta autenticar en `POST /login`, entonces el sistema responde con código HTTP 401 y el mensaje `"credenciales inválidas"`. |
| CA-004 | Dado un usuario inactivo (campo `activo = false`) con credenciales correctas, cuando intenta autenticarse en `POST /login`, entonces el sistema responde con código HTTP 401 y no genera token. |
| CA-005 | Dado que se omite el campo `nombre` o `clave` en el cuerpo de la petición, cuando se llama al endpoint `POST /login`, entonces el sistema responde con código HTTP 400 y el mensaje `"nombre y clave requeridos"`. |
| CA-006 | Dado un usuario con rol `ADMIN` que se autentica exitosamente, cuando el frontend recibe el token, entonces redirige al usuario a la ruta `/adminhome`. |
| CA-007 | Dado un usuario con rol diferente a `ADMIN` que se autentica exitosamente, cuando el frontend recibe el token, entonces redirige al usuario a la ruta `/test`. |
| CA-008 | Dado que la contraseña ingresada tiene menos de 8 caracteres o no cumple la política (mayúscula, minúscula, carácter especial), cuando el usuario intenta iniciar sesión desde el formulario, entonces el frontend muestra el mensaje de validación sin enviar la petición al servidor. |

---

## Módulo: Maestra 1 — Tipo de Pregunta (`/api/tipo-pregunta`)

| ID     | Criterio de aceptación |
|--------|------------------------|
| CA-009 | Dado que existen registros en la tabla `tipos_preguntas`, cuando se realiza una petición `GET /api/tipo-pregunta`, entonces el sistema responde con código HTTP 200 y un arreglo JSON con todos los tipos de pregunta. |
| CA-010 | Dado un ID válido de tipo de pregunta existente, cuando se realiza `GET /api/tipo-pregunta?id={id}`, entonces el sistema responde con código HTTP 200 y el objeto JSON del registro correspondiente. |
| CA-011 | Dado un ID que no existe en la tabla `tipos_preguntas`, cuando se realiza `GET /api/tipo-pregunta?id={id}`, entonces el sistema responde con código HTTP 404 y el mensaje `"TipoPregunta no encontrada"`. |
| CA-012 | Dado un cuerpo JSON con los campos `nombre` y `descripcion` válidos, cuando se realiza `POST /api/tipo-pregunta`, entonces el sistema crea el registro, responde con código HTTP 201, retorna el mensaje de confirmación y el `id` generado, y el registro aparece al hacer `GET`. |
| CA-013 | Dado un cuerpo JSON sin el campo `nombre` o con `nombre` vacío, cuando se realiza `POST /api/tipo-pregunta`, entonces el sistema responde con código HTTP 400 y el mensaje `"El campo 'nombre' es obligatorio"`, sin insertar ningún registro. |
| CA-014 | Dado un registro existente en `tipos_preguntas`, cuando se realiza `PUT /api/tipo-pregunta` con el `id` y el nuevo valor de `nombre`, entonces el sistema actualiza el registro, responde con código HTTP 200 y el cambio se refleja al consultar el registro. |
| CA-015 | Dado un `PUT /api/tipo-pregunta` sin el campo `id` en el cuerpo, cuando se envía la petición, entonces el sistema responde con código HTTP 400 y el mensaje `"El campo 'id' es obligatorio"`. |
| CA-016 | Dado un ID válido existente en `tipos_preguntas`, cuando se realiza `DELETE /api/tipo-pregunta?id={id}`, entonces el sistema elimina el registro, responde con código HTTP 200 y el registro ya no aparece al hacer `GET`. |
| CA-017 | Dado un ID que no existe en `tipos_preguntas`, cuando se realiza `DELETE /api/tipo-pregunta?id={id}`, entonces el sistema responde con código HTTP 404 y el mensaje `"TipoPregunta no encontrada"`. |

---

## Módulo: Transaccional — Pregunta (`/api/pregunta`)

| ID     | Criterio de aceptación |
|--------|------------------------|
| CA-018 | Dado que existen registros en la tabla `preguntas`, cuando se realiza una petición `GET /api/pregunta`, entonces el sistema responde con código HTTP 200 y un arreglo JSON con todas las preguntas. |
| CA-019 | Dado un ID válido de pregunta existente, cuando se realiza `GET /api/pregunta?id={id}`, entonces el sistema responde con código HTTP 200 y el objeto JSON con los campos `id`, `enunciado` y `tipoPregunta`. |
| CA-020 | Dado un ID que no existe en la tabla `preguntas`, cuando se realiza `GET /api/pregunta?id={id}`, entonces el sistema responde con código HTTP 404 y el mensaje `"Pregunta no encontrada"`. |
| CA-021 | Dado un cuerpo JSON con `enunciado` y `tipoPregunta` válidos, cuando se realiza `POST /api/pregunta`, entonces el sistema crea el registro, responde con código HTTP 201, retorna el `id` generado y el registro aparece en el listado. |
| CA-022 | Dado un cuerpo JSON sin el campo `enunciado` o con `enunciado` vacío, cuando se realiza `POST /api/pregunta`, entonces el sistema responde con código HTTP 400 y el mensaje `"El campo 'enunciado' es obligatorio"`, sin insertar ningún registro en la base de datos. |
| CA-023 | Dado un registro existente en `preguntas`, cuando se realiza `PUT /api/pregunta` con el `id` y el nuevo valor de `enunciado`, entonces el sistema actualiza el registro, responde con código HTTP 200 y el cambio se refleja al consultar el registro. |
| CA-024 | Dado un `PUT /api/pregunta` sin el campo `id` en el cuerpo, cuando se envía la petición, entonces el sistema responde con código HTTP 400 y el mensaje `"El campo 'id' es obligatorio"`. |
| CA-025 | Dado un ID válido existente en `preguntas`, cuando se realiza `DELETE /api/pregunta?id={id}`, entonces el sistema elimina el registro, responde con código HTTP 200 con el mensaje `"Pregunta eliminada correctamente"` y el registro ya no aparece en el listado. |
| CA-026 | Dado un ID que no existe en `preguntas`, cuando se realiza `DELETE /api/pregunta?id={id}`, entonces el sistema responde con código HTTP 404 y el mensaje `"Pregunta no encontrada"`. |
| CA-027 | Dado un `DELETE /api/pregunta` sin el parámetro `id`, cuando se envía la petición, entonces el sistema responde con código HTTP 400 y el mensaje `"El parámetro 'id' es obligatorio"`. |

---

## Resumen

| Módulo                  | Cantidad de criterios |
|-------------------------|-----------------------|
| Login                   | 8 (CA-001 a CA-008)   |
| Maestra — Tipo Pregunta | 9 (CA-009 a CA-017)   |
| Transaccional — Pregunta| 10 (CA-018 a CA-027)  |
| **TOTAL**               | **27 criterios**      |
