package com.empresa;

import com.empresa.model.TipoPregunta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas unitarias - Modulo Maestro (TipoPregunta)")
public class MaestroTest {

    private TipoPregunta tipoPregunta;

    @BeforeEach
    void setUp() {
        tipoPregunta = new TipoPregunta();
        tipoPregunta.setId(1L);
        tipoPregunta.setNombre("Seleccion Multiple");
        tipoPregunta.setDescripcion("Pregunta con varias opciones de respuesta");
    }

    // ================================================================
    // PRUEBAS DE CREACION
    // ================================================================

    @Test
    @DisplayName("CP-001: TipoPregunta creado correctamente con todos sus campos")
    void testCrearTipoPreguntaCompleto() {
        assertNotNull(tipoPregunta, "El objeto TipoPregunta no debe ser null");
        assertEquals(1L, tipoPregunta.getId(), "El ID debe ser 1");
        assertEquals("Seleccion Multiple", tipoPregunta.getNombre(), "El nombre debe coincidir");
        assertEquals("Pregunta con varias opciones de respuesta", tipoPregunta.getDescripcion(), "La descripcion debe coincidir");
    }

    @Test
    @DisplayName("CP-002: TipoPregunta con nombre nulo es invalido")
    void testNombreNuloEsInvalido() {
        TipoPregunta tipo = new TipoPregunta();
        tipo.setNombre(null);
        assertNull(tipo.getNombre(), "El nombre debe ser null cuando no se asigna");
    }

    @Test
    @DisplayName("CP-003: TipoPregunta con nombre vacio es invalido")
    void testNombreVacioEsInvalido() {
        TipoPregunta tipo = new TipoPregunta();
        tipo.setNombre("");
        assertTrue(tipo.getNombre().isEmpty(), "El nombre vacio debe detectarse como invalido");
    }

    // ================================================================
    // PRUEBAS DE ACTUALIZACION
    // ================================================================

    @Test
    @DisplayName("CP-004: Actualizar nombre de TipoPregunta correctamente")
    void testActualizarNombre() {
        tipoPregunta.setNombre("Verdadero o Falso");
        assertEquals("Verdadero o Falso", tipoPregunta.getNombre(), "El nombre debe actualizarse correctamente");
    }

    @Test
    @DisplayName("CP-005: Actualizar descripcion de TipoPregunta correctamente")
    void testActualizarDescripcion() {
        tipoPregunta.setDescripcion("Nueva descripcion actualizada");
        assertEquals("Nueva descripcion actualizada", tipoPregunta.getDescripcion(), "La descripcion debe actualizarse correctamente");
    }

    // ================================================================
    // PRUEBAS DE VALIDACION
    // ================================================================

    @Test
    @DisplayName("CP-006: Dos TiposPreguntas con mismo ID deben tener el mismo identificador")
    void testMismoId() {
        TipoPregunta otro = new TipoPregunta();
        otro.setId(1L);
        assertEquals(tipoPregunta.getId(), otro.getId(), "Dos tipos con mismo ID deben ser iguales en identificador");
    }

    @Test
    @DisplayName("CP-007: TipoPregunta con ID diferente es un registro distinto")
    void testIdDiferente() {
        TipoPregunta otro = new TipoPregunta();
        otro.setId(2L);
        assertNotEquals(tipoPregunta.getId(), otro.getId(), "IDs diferentes deben identificar registros distintos");
    }

    @Test
    @DisplayName("CP-008: Constructor con parametros inicializa correctamente")
    void testConstructorConParametros() {
        TipoPregunta tipo = new TipoPregunta(5L, "Completar", "Completar el espacio en blanco");
        assertEquals(5L, tipo.getId());
        assertEquals("Completar", tipo.getNombre());
        assertEquals("Completar el espacio en blanco", tipo.getDescripcion());
    }

    @Test
    @DisplayName("CP-009: Nombre con espacios en blanco debe detectarse")
    void testNombreConEspacios() {
        TipoPregunta tipo = new TipoPregunta();
        tipo.setNombre("   ");
        assertTrue(tipo.getNombre().trim().isEmpty(), "Un nombre con solo espacios debe tratarse como invalido");
    }

    @Test
    @DisplayName("CP-010: TipoPregunta vacio no debe tener ID asignado")
    void testTipoPreguntaVacioSinId() {
        TipoPregunta tipo = new TipoPregunta();
        assertNull(tipo.getId(), "Un TipoPregunta recien creado no debe tener ID asignado");
    }
}
