# Plan de Pruebas — SAFAB
**Sistema Adaptativo de Funciones en Algoritmia Básica**

**Repositorio:** `pruebas-acosta-higuita`  
**Integrantes:** Alejandro Acosta — Juan David Higuita  
**Fecha de elaboración:** Junio 2025  
**Versión:** 1.0

---

## 1. Descripción del sistema

SAFAB es una aplicación web educativa que permite a los estudiantes practicar algoritmia básica mediante pruebas de selección múltiple. Los administradores gestionan el banco de preguntas y sus tipos, mientras que los estudiantes responden pruebas aleatorias y acumulan puntaje. El sistema registra logs de actividad y controla el acceso mediante autenticación JWT.

---

## 2. Tecnologías utilizadas

| Componente        | Tecnología                          |
|-------------------|-------------------------------------|
| Lenguaje backend  | Java 17                             |
| Framework backend | Jakarta Servlets (sin Spring)       |
| ORM               | Hibernate 6.5 / JPA 3.1            |
| Base de datos      | MySQL 8 (puerto 3307, BD: empresa_db) |
| Autenticación     | JWT (jjwt 0.12.6) + BCrypt          |
| Framework frontend| React 18 + Vite                     |
| UI frontend       | React Bootstrap                     |
| Servidor          | Apache Tomcat (WAR)                 |
| Gestor de deps.   | Maven                               |

---

## 3. Alcance de las pruebas

### 3.1 Módulos que SE van a probar

| Módulo             | Descripción                                         |
|--------------------|-----------------------------------------------------|
| **Login**          | Autenticación de usuarios, generación de token JWT, validación de estado activo/inactivo |
| **Maestra 1 — Tipo de Pregunta** | CRUD completo sobre la tabla `tipos_preguntas` (endpoint `/api/tipo-pregunta`) |
| **Transaccional — Pregunta** | CRUD completo sobre la tabla `preguntas` (endpoint `/api/pregunta`), incluyendo validación de campos obligatorios y relación con tipo de pregunta |

### 3.2 Módulos que NO se van a probar

| Módulo             | Justificación                                       |
|--------------------|-----------------------------------------------------|
| Registro de usuarios (`/registro`) | Fuera del alcance definido por el docente |
| Dashboard / reportes | Solo lectura, sin lógica de negocio crítica |
| Logs (`/log`)      | Funcionalidad auxiliar, no es módulo principal |
| Tabla `respuestas` | No tiene servlet propio ni es módulo maestro aprobado |
| Tabla `pruebas` (resultados) | No es el módulo transaccional definido por el docente |

---

## 4. Tipos de prueba a ejecutar

| Tipo de prueba        | Herramienta        | Módulos cubiertos                  |
|-----------------------|--------------------|------------------------------------|
| Pruebas unitarias     | JUnit 5            | Login, Tipo de Pregunta, Pregunta  |
| Pruebas funcionales   | Selenium IDE       | Login, Tipo de Pregunta, Pregunta  |
| Pruebas de API REST   | Postman            | Login, Tipo de Pregunta, Pregunta  |
| Pruebas de BD         | MySQL Workbench    | `tipos_preguntas`, `preguntas`     |
| Pruebas de seguridad  | OWASP ZAP          | Aplicación web completa            |

---

## 5. Ambiente de prueba

| Elemento              | Valor                                         |
|-----------------------|-----------------------------------------------|
| Sistema operativo     | Windows 10/11                                 |
| Navegador             | Google Chrome (última versión estable)        |
| Java                  | JDK 17                                        |
| Maven                 | 3.9.x                                         |
| MySQL                 | 8.0 (puerto 3307)                             |
| Base de datos         | `empresa_db`                                  |
| Servidor backend      | Apache Tomcat — `http://localhost:8080`       |
| URL backend           | `http://localhost:8080/appeducativa-backend`  |
| Frontend              | Vite dev server — `http://localhost:5173`     |
| JUnit                 | 5.10.2                                        |
| Selenium IDE          | Extensión Chrome                              |
| Postman               | Versión de escritorio                         |
| OWASP ZAP             | 2.15.x                                        |

---

## 6. Criterios de entrada y salida

### Criterios de entrada (para iniciar las pruebas)
- El backend compila y despliega correctamente en Tomcat sin errores.
- La base de datos `empresa_db` está activa y contiene al menos un usuario administrador.
- El frontend corre en `localhost:5173` y se conecta al backend.
- Los datos de prueba han sido insertados con el script `datos-prueba.sql`.

### Criterios de salida (para dar por terminadas las pruebas)
- El 100% de los casos de prueba han sido ejecutados.
- Los defectos críticos y altos han sido documentados con propuesta de solución.
- Todas las evidencias (capturas con hora visible) han sido recopiladas.
- El reporte final ha sido consolidado.

---

## 7. Riesgos identificados y mitigación

| ID  | Riesgo                                              | Probabilidad | Impacto | Mitigación                                                    |
|-----|-----------------------------------------------------|--------------|---------|---------------------------------------------------------------|
| R01 | El sistema no levanta por problemas de configuración de BD | Media | Alto | Verificar `persistence.xml` y que MySQL esté en puerto 3307 antes de iniciar |
| R02 | Datos de prueba inconsistentes entre ejecuciones    | Media        | Medio   | Ejecutar el script de limpieza antes de cada ciclo de pruebas |
| R03 | El token JWT expira durante la ejecución de pruebas | Baja         | Medio   | Ajustar `jwt.expiration` a 3600000 ms (1 hora) en `config.properties` |
| R04 | Selenium IDE falla por cambios en el DOM del frontend | Media       | Medio   | Grabar los scripts con la versión estable del frontend        |
| R05 | OWASP ZAP genera falsos positivos                   | Alta         | Bajo    | Documentar y analizar cada alerta manualmente antes de reportar |
| R06 | Diferencias entre el diagrama original y el código implementado | Alta | Bajo | Las pruebas se basan en el código fuente real, no en el diagrama |

---

## 8. Entregables

| Entregable                          | Ubicación en el repositorio               |
|-------------------------------------|-------------------------------------------|
| Plan de pruebas (este documento)    | `01-planificacion/plan-de-pruebas.md`     |
| Criterios de aceptación             | `01-planificacion/criterios-aceptacion.md`|
| Clases JUnit 5                      | `02-pruebas-unitarias/src/test/java/`     |
| Suite Selenium IDE                  | `03-pruebas-funcionales/`                 |
| Colección Postman                   | `04-pruebas-api/`                         |
| Scripts SQL                         | `05-base-de-datos/`                       |
| Reporte OWASP ZAP                   | `06-seguridad/`                           |
| Reporte final + videos              | `07-entrega-final/`                       |
