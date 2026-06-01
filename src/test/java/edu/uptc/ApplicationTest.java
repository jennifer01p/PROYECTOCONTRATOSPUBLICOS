package edu.uptc;

import edu.uptc.dominio.*;
import edu.uptc.enums.*;
import edu.uptc.servicio.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del sistema de contratos públicos SECOP II.
 * Cubre los casos más importantes de validación, CRUD y lógica de negocio.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
class ApplicationTest {

    private UsuarioServicio usuarioServicio;
    private ContratoServicio contratoServicio;
    private ReporteServicio reporteServicio;
    private SeguridadServicio seguridadServicio;

    private Contratante contratante;
    private Contratista contratista;

    /**
     * Inicializa los servicios y datos comunes antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        usuarioServicio = new UsuarioServicio();
        contratoServicio = new ContratoServicio();
        reporteServicio = new ReporteServicio();

        contratante = new Contratante(TipoPersona.JURIDICA, TipoDocumento.NIT,
                "900123456", "Alcaldía de Tunja", "contratante@tunja.gov.co",
                "clave123", "6087412580", "Calle 19", "Tunja",
                "Gobierno", NivelEntidad.MUNICIPAL, "ENT-001");

        contratista = new Contratista(TipoPersona.NATURAL, TipoDocumento.CC,
                "1234567890", "Carlos Pérez", "carlos@gmail.com",
                "clave456", "3109876543", "Carrera 5", "Tunja", false, "Ingeniería Civil");

        usuarioServicio.crearUsuario(contratante);
        usuarioServicio.crearUsuario(contratista);
        seguridadServicio = new SeguridadServicio(usuarioServicio.getUsuarios());
    }

    // ===================== PRUEBAS DE USUARIO =====================

    /**
     * Verifica que un usuario se registra y puede buscarse correctamente.
     */
    @Test
    @DisplayName("Crear y buscar usuario")
    void testCrearYBuscarUsuario() {
        Usuario encontrado = usuarioServicio.buscarUsuario("1234567890");
        assertNotNull(encontrado, "El usuario debe existir");
        assertEquals("Carlos Pérez", encontrado.getNombre());
    }

    /**
     * Verifica que buscar un documento inexistente retorna null.
     */
    @Test
    @DisplayName("Buscar usuario inexistente retorna null")
    void testBuscarUsuarioInexistente() {
        assertNull(usuarioServicio.buscarUsuario("9999999999"));
    }

    /**
     * Verifica que un usuario puede actualizarse correctamente.
     */
    @Test
    @DisplayName("Actualizar usuario")
    void testActualizarUsuario() {
        String resultado = usuarioServicio.actualizarUsuario("1234567890",
                TipoPersona.NATURAL, "nuevo@gmail.com", "nuevaClave", "Av Nueva", "Bogotá");
        assertEquals("Información de usuario actualizada", resultado);
        assertEquals("nuevo@gmail.com", contratista.getCorreo());
    }

    /**
     * Verifica que eliminar un usuario existente lo remueve del sistema.
     */
    @Test
    @DisplayName("Eliminar usuario existente")
    void testEliminarUsuario() {
        String resultado = usuarioServicio.eliminarUsuario("1234567890");
        assertEquals("Usuario eliminado", resultado);
        assertNull(usuarioServicio.buscarUsuario("1234567890"));
    }

    // ===================== PRUEBAS DE SEGURIDAD =====================

    /**
     * Verifica que el login con credenciales correctas retorna el usuario.
     */
    @Test
    @DisplayName("Login exitoso")
    void testLoginExitoso() {
        Usuario u = seguridadServicio.iniciarSesion("carlos@gmail.com", "clave456");
        assertNotNull(u);
        assertEquals("Carlos Pérez", u.getNombre());
    }

    /**
     * Verifica que el login con contraseña incorrecta retorna null.
     */
    @Test
    @DisplayName("Login fallido con contraseña incorrecta")
    void testLoginFallido() {
        assertNull(seguridadServicio.iniciarSesion("carlos@gmail.com", "incorrecta"));
    }

    // ===================== PRUEBAS DE CONTRATOS =====================

    /**
     * Verifica que un ContratoPrestacionServicios válido pasa la validación.
     */
    @Test
    @DisplayName("Validar ContratoPrestacionServicios correcto")
    void testValidacionPrestacionServiciosValido() {
        // honorario 2_000_000 * 6 meses = 12_000_000 == valorContrato
        ContratoPrestacionServicios c = new ContratoPrestacionServicios(
                LocalDate.now(), "C001", "Consultoría TI", 12_000_000,
                6, EstadoContrato.PUBLICADO, TipoContrato.PRESENTACION_SERVICIOS,
                contratista, contratante, "Ingeniero de Sistemas", 2_000_000, 6);
        assertTrue(c.validarContrato(), "El contrato de prestación de servicios debe ser válido");
    }

    /**
     * Verifica que un ContratoPrestacionServicios con valores inconsistentes falla la validación.
     */
    @Test
    @DisplayName("Validar ContratoPrestacionServicios incorrecto")
    void testValidacionPrestacionServiciosInvalido() {
        ContratoPrestacionServicios c = new ContratoPrestacionServicios(
                LocalDate.now(), "C002", "Consultoría TI", 15_000_000,
                6, EstadoContrato.PUBLICADO, TipoContrato.PRESENTACION_SERVICIOS,
                contratista, contratante, "Ingeniero", 2_000_000, 6);
        assertFalse(c.validarContrato(), "El contrato con valores inconsistentes no debe ser válido");
    }

    /**
     * Verifica que un ContratoCompraventa válido pasa la validación.
     */
    @Test
    @DisplayName("Validar ContratoCompraventa correcto")
    void testValidacionCompraventaValido() {
        // 500_000 * 10 = 5_000_000
        ContratoCompraventa c = new ContratoCompraventa(
                LocalDate.now(), "C003", "Compra de sillas", 5_000_000,
                1, EstadoContrato.PUBLICADO, TipoContrato.COMPRAVENTA,
                contratista, contratante, "Silla ergonómica", "Rimax", "OfficeMax",
                "SER-001", 500_000, 10);
        assertTrue(c.validarContrato());
    }

    /**
     * Verifica que un ContratoObrasPublicas con datos completos pasa la validación.
     */
    @Test
    @DisplayName("Validar ContratoObrasPublicas correcto")
    void testValidacionObrasPublicasValido() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C004", "Construcción parque", 80_000_000,
                12, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "Calle 10 # 5-20 Tunja", 500.0);
        assertTrue(c.validarContrato());
    }

    /**
     * Verifica que crear y consultar un contrato por ID funciona correctamente.
     */
    @Test
    @DisplayName("Crear y consultar contrato")
    void testCrearYConsultarContrato() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C005", "Obra vial", 50_000_000,
                8, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "Av. Norte Tunja", 200.0);
        contratoServicio.crearContrato(c);
        assertNotNull(contratoServicio.consultarContrato("C005"));
    }

    /**
     * Verifica que cambiar el estado de un contrato funciona correctamente.
     */
    @Test
    @DisplayName("Cambiar estado de contrato")
    void testCambiarEstadoContrato() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C006", "Obra peatonal", 30_000_000,
                4, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "Carrera 9 Tunja", 100.0);
        contratoServicio.crearContrato(c);
        String resultado = contratoServicio.cambiarEstadoContrato("C006", EstadoContrato.LICITACION);
        assertTrue(resultado.contains("LICITACION"));
        assertEquals(EstadoContrato.LICITACION, c.getEstado());
    }

    /**
     * Verifica que eliminar un contrato lo remueve del sistema.
     */
    @Test
    @DisplayName("Eliminar contrato existente")
    void testEliminarContrato() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C007", "Acueducto", 20_000_000,
                3, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "Rural Tunja", 50.0);
        contratoServicio.crearContrato(c);
        String res = contratoServicio.eliminar("C007");
        assertTrue(res.contains("eliminado"));
        assertNull(contratoServicio.consultarContrato("C007"));
    }

    // ===================== PRUEBAS DE REPORTE =====================

    /**
     * Verifica que se genera un reporte de interventoría al cambiar el estado.
     */
    @Test
    @DisplayName("Crear reporte de interventoría")
    void testCrearReporte() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C008", "Electrificación", 10_000_000,
                2, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "Sector rural", 80.0);
        contratoServicio.crearContrato(c);
        contratoServicio.cambiarEstadoContrato("C008", EstadoContrato.EJECUCION);
        reporteServicio.crearReporte(c, "Inicio de ejecución de la obra");

        ArrayList<ReporteInventoria> reportes = reporteServicio.consultarReportesPorContrato("C008");
        assertEquals(1, reportes.size());
        assertEquals("Inicio de ejecución de la obra", reportes.get(0).getInforme());
    }
}
