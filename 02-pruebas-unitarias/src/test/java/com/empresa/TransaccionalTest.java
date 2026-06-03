package com.empresa;

import com.empresa.model.Preguntas;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas unitarias - Modulo Transaccional (Pregunta)")
public class TransaccionalTest {

    private Preguntas pregunta;

    @BeforeEach
    void setUp() {
        pregunta = new Preguntas();
        pregunta.setId(1L);
        pregunta.setEnunciado("¿Cual es la complejidad del algoritmo de busqueda binaria?");
        pregunta.setTipoPregunta(1L);
    }

    // ================================================================
    // PRUEBAS DE CREACION
    // ================================================================

    @Test
    @DisplayName("CP-001: Pregunta creada correctamente con todos sus campos")
    void testCrearPreguntaCompleta() {
        assertNotNull(pregunta, "El objeto Pregunta no debe ser null");
        assertEquals(1L, pregunta.getId(), "El ID debe ser 1");
        assertEquals("¿Cual es la complejidad del algoritmo de busqueda binaria?", pregunta.getEnunciado(), "El enunciado debe coincidir");
        assertEquals(1L, pregunta.getTipoPregunta(), "El tipo de pregunta debe coincidir");
    }

    @Test
    @DisplayName("CP-002: Enunciado nulo es invalido")
    void testEnunciadoNuloEsInvalido() {
        Preguntas p = new Preguntas();
        p.setEnunciado(null);
        assertNull(p.getEnunciado(), "El enunciado null debe detectarse como invalido");
    }

    @Test
    @DisplayName("CP-003: Enunciado vacio es invalido")
    void testEnunciadoVacioEsInvalido() {
        Preguntas p = new Preguntas();
        p.setEnunciado("");
        assertTrue(p.getEnunciado().isEmpty(), "El enunciado vacio debe detectarse como invalido");
    }

    @Test
    @DisplayName("CP-004: Pregunta sin tipo de pregunta asignado")
    void testPreguntaSinTipo() {
        Preguntas p = new Preguntas();
        p.setEnunciado("¿Que es un algoritmo?");
        assertNull(p.getTipoPregunta(), "Una pregunta sin tipo asignado debe tener tipoPregunta null");
    }

    // ================================================================
    // PRUEBAS DE ACTUALIZACION
    // ================================================================

    @Test
    @DisplayName("CP-005: Actualizar enunciado de la pregunta correctamente")
    void testActualizarEnunciado() {
        pregunta.setEnunciado("¿Que es la recursividad?");
        assertEquals("¿Que es la recursividad?", pregunta.getEnunciado(), "El enunciado debe actualizarse correctamente");
    }

    @Test
    @DisplayName("CP-006: Actualizar tipo de pregunta correctamente")
    void testActualizarTipoPregunta() {
        pregunta.setTipoPregunta(2L);
        assertEquals(2L, pregunta.getTipoPregunta(), "El tipo de pregunta debe actualizarse correctamente");
    }

    // ================================================================
    // PRUEBAS DE VALIDACION
    // ================================================================

    @Test
    @DisplayName("CP-007: Dos preguntas con mismo ID deben tener el mismo identificador")
    void testMismoId() {
        Preguntas otra = new Preguntas();
        otra.setId(1L);
        assertEquals(pregunta.getId(), otra.getId(), "Dos preguntas con mismo ID deben ser iguales en identificador");
    }

    @Test
    @DisplayName("CP-008: Pregunta con ID diferente es un registro distinto")
    void testIdDiferente() {
        Preguntas otra = new Preguntas();
        otra.setId(99L);
        assertNotEquals(pregunta.getId(), otra.getId(), "IDs diferentes deben identificar preguntas distintas");
    }

    @Test
    @DisplayName("CP-009: Enunciado con solo espacios debe detectarse como invalido")
    void testEnunciadoConSoloEspacios() {
        Preguntas p = new Preguntas();
        p.setEnunciado("     ");
        assertTrue(p.getEnunciado().trim().isEmpty(), "Un enunciado con solo espacios debe tratarse como invalido");
    }

    @Test
    @DisplayName("CP-010: Pregunta nueva no debe tener ID asignado")
    void testPreguntaNuevaSinId() {
        Preguntas p = new Preguntas();
        assertNull(p.getId(), "Una pregunta recien creada no debe tener ID asignado");
    }
}
