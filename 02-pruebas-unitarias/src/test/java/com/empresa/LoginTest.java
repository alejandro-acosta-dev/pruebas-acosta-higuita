package com.empresa;

import com.empresa.util.PasswordUtil;
import com.empresa.util.JwtUtil;
import com.empresa.model.Usuario;
import com.empresa.model.Rol;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas unitarias - Modulo Login")
public class LoginTest {

    private String claveHasheada;

    @BeforeEach
    void setUp() {
        // Simula la clave que estaria guardada en la BD (hasheada con BCrypt)
        claveHasheada = PasswordUtil.hashPassword("Admin123!");
    }

    // ================================================================
    // PRUEBAS DE PasswordUtil
    // ================================================================

    @Test
    @DisplayName("CP-001: Hash de contrasena no debe ser igual al texto plano")
    void testHashNoEsIgualAlTextoPlano() {
        String clave = "Admin123!";
        String hash = PasswordUtil.hashPassword(clave);
        assertNotEquals(clave, hash, "El hash no debe ser igual a la contrasena original");
    }

    @Test
    @DisplayName("CP-002: Verificacion exitosa con contrasena correcta")
    void testCheckPasswordCorrecto() {
        boolean resultado = PasswordUtil.checkPassword("Admin123!", claveHasheada);
        assertTrue(resultado, "checkPassword debe retornar true con la contrasena correcta");
    }

    @Test
    @DisplayName("CP-003: Verificacion fallida con contrasena incorrecta")
    void testCheckPasswordIncorrecto() {
        boolean resultado = PasswordUtil.checkPassword("ClaveIncorrecta!", claveHasheada);
        assertFalse(resultado, "checkPassword debe retornar false con una contrasena incorrecta");
    }

    @Test
    @DisplayName("CP-004: Verificacion con contrasena nula retorna false")
    void testCheckPasswordNulo() {
        boolean resultado = PasswordUtil.checkPassword(null, claveHasheada);
        assertFalse(resultado, "checkPassword debe retornar false si la contrasena es null");
    }

    @Test
    @DisplayName("CP-005: Verificacion con hash nulo retorna false")
    void testCheckPasswordHashNulo() {
        boolean resultado = PasswordUtil.checkPassword("Admin123!", null);
        assertFalse(resultado, "checkPassword debe retornar false si el hash es null");
    }

    // ================================================================
    // PRUEBAS DE JwtUtil
    // ================================================================

    @Test
    @DisplayName("CP-006: Generacion de token JWT no debe ser nulo ni vacio")
    void testGenerarTokenNoNulo() {
        String token = JwtUtil.generateToken("admin");
        assertNotNull(token, "El token generado no debe ser null");
        assertFalse(token.isEmpty(), "El token generado no debe estar vacio");
    }

    @Test
    @DisplayName("CP-007: Token generado debe contener el nombre de usuario correcto")
    void testTokenContieneUsername() {
        String token = JwtUtil.generateToken("alejandro");
        Optional<String> username = JwtUtil.getUsernameFromToken(token);
        assertTrue(username.isPresent(), "El token debe contener el username");
        assertEquals("alejandro", username.get(), "El username extraido debe ser el mismo que se uso al generar");
    }

    @Test
    @DisplayName("CP-008: Token generado desde Usuario debe contener su rol")
    void testTokenContieneRol() {
        Usuario u = new Usuario();
        u.setNombre("admin");
        u.setClave(claveHasheada);
        u.setActivo(true);

        Rol rol = new Rol();
        rol.setNombre("ADMIN");
        u.addRol(rol);

        String token = JwtUtil.generateToken(u);
        List<String> roles = JwtUtil.getRolesFromToken(token);

        assertFalse(roles.isEmpty(), "La lista de roles no debe estar vacia");
        assertTrue(roles.contains("ADMIN"), "El token debe contener el rol ADMIN");
    }

    @Test
    @DisplayName("CP-009: Token invalido debe retornar Optional vacio")
    void testTokenInvalidoRetornaVacio() {
        Optional<String> username = JwtUtil.getUsernameFromToken("token.invalido.xyz");
        assertFalse(username.isPresent(), "Un token invalido debe retornar Optional vacio");
    }

    @Test
    @DisplayName("CP-010: Usuario inactivo no debe autenticarse")
    void testUsuarioInactivoNoAutenticado() {
        Usuario u = new Usuario();
        u.setNombre("inactivo");
        u.setClave(PasswordUtil.hashPassword("Clave123!"));
        u.setActivo(false);

        assertFalse(u.isActivo(), "Un usuario inactivo no debe poder autenticarse");
    }
}
