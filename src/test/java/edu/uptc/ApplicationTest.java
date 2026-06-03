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
     * Inicializa servicios y datos comunes antes de cada prueba.
     */
    @BeforeEach
    void setUp() {
        usuarioServicio  = new UsuarioServicio();
        contratoServicio = new ContratoServicio();
        reporteServicio  = new ReporteServicio();

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

    // ===================== USUARIOS =====================

    /**
     * Verifica que un usuario registrado puede encontrarse por documento.
     */
    @Test
    @DisplayName("Crear y buscar usuario por documento")
    void testCrearYBuscarUsuario() {
        Usuario encontrado = usuarioServicio.buscarUsuario("1234567890");
        assertNotNull(encontrado);
        assertEquals("Carlos Pérez", encontrado.getNombre());
    }

    /**
     * Verifica que registrar un usuario con documento único retorna mensaje de éxito.
     */
    @Test
    @DisplayName("Crear usuario con documento único retorna éxito")
    void testCrearUsuarioDocumentoUnico() {
        Contratista nuevo = new Contratista(TipoPersona.NATURAL, TipoDocumento.CC,
                "9999999999", "Ana Gómez", "ana@mail.com",
                "clave", "300000000", "Calle 1", "Bogotá", false, "Derecho");
        String resultado = usuarioServicio.crearUsuario(nuevo);
        assertEquals("Usuario registrado exitosamente.", resultado);
    }

    /**
     * Verifica que registrar un usuario con documento duplicado es rechazado.
     */
    @Test
    @DisplayName("Crear usuario con documento duplicado es rechazado")
    void testCrearUsuarioDocumentoDuplicado() {
        Contratista duplicado = new Contratista(TipoPersona.NATURAL, TipoDocumento.CC,
                "1234567890", "Otro Nombre", "otro@mail.com",
                "clave", "300000000", "Calle 1", "Bogotá", false, "Sistemas");
        String resultado = usuarioServicio.crearUsuario(duplicado);
        assertTrue(resultado.contains("Ya existe"));
    }

    /**
     * Verifica que existeDocumento retorna true para un documento registrado.
     */
    @Test
    @DisplayName("existeDocumento retorna true para documento registrado")
    void testExisteDocumentoRegistrado() {
        assertTrue(usuarioServicio.existeDocumento("1234567890"));
    }

    /**
     * Verifica que existeDocumento retorna false para un documento no registrado.
     */
    @Test
    @DisplayName("existeDocumento retorna false para documento no registrado")
    void testExisteDocumentoNoRegistrado() {
        assertFalse(usuarioServicio.existeDocumento("0000000000"));
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
     * Verifica que los datos de un usuario se actualizan correctamente.
     */
    @Test
    @DisplayName("Actualizar usuario existente")
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

    /**
     * Verifica que eliminar un usuario inexistente retorna el mensaje adecuado.
     */
    @Test
    @DisplayName("Eliminar usuario inexistente")
    void testEliminarUsuarioInexistente() {
        String resultado = usuarioServicio.eliminarUsuario("0000000000");
        assertEquals("Usuario no encontrado", resultado);
    }

    // ===================== SEGURIDAD =====================

    /**
     * Verifica que el login con credenciales correctas retorna el usuario.
     */
    @Test
    @DisplayName("Login exitoso con credenciales correctas")
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
    void testLoginContrasenaIncorrecta() {
        assertNull(seguridadServicio.iniciarSesion("carlos@gmail.com", "incorrecta"));
    }

    /**
     * Verifica que el login con correo inexistente retorna null.
     */
    @Test
    @DisplayName("Login fallido con correo inexistente")
    void testLoginCorreoInexistente() {
        assertNull(seguridadServicio.iniciarSesion("noexiste@mail.com", "clave456"));
    }

    // ===================== CONTRATOS - PRESTACIÓN DE SERVICIOS =====================

    /**
     * Verifica que un ContratoPrestacionServicios con valores consistentes es válido.
     * honorario(2_000_000) * meses(6) = 12_000_000 == valorContrato
     */
    @Test
    @DisplayName("Validar ContratoPrestacionServicios válido")
    void testValidacionPrestacionServiciosValido() {
        ContratoPrestacionServicios c = new ContratoPrestacionServicios(
                LocalDate.now(), "C001", "Consultoría TI", 12_000_000,
                6, EstadoContrato.PUBLICADO, TipoContrato.PRESENTACION_SERVICIOS,
                contratista, contratante, "Ingeniero de Sistemas", 2_000_000, 6);
        assertTrue(c.validarContrato());
    }

    /**
     * Verifica que un ContratoPrestacionServicios con valores inconsistentes es inválido.
     * honorario(2_000_000) * meses(6) = 12_000_000 != valorContrato(15_000_000)
     */
    @Test
    @DisplayName("Validar ContratoPrestacionServicios inválido")
    void testValidacionPrestacionServiciosInvalido() {
        ContratoPrestacionServicios c = new ContratoPrestacionServicios(
                LocalDate.now(), "C002", "Consultoría TI", 15_000_000,
                6, EstadoContrato.PUBLICADO, TipoContrato.PRESENTACION_SERVICIOS,
                contratista, contratante, "Ingeniero", 2_000_000, 6);
        assertFalse(c.validarContrato());
    }

    // ===================== CONTRATOS - COMPRAVENTA =====================

    /**
     * Verifica que un ContratoCompraventa con valores consistentes es válido.
     * valorUnitario(500_000) * cantidad(10) = 5_000_000 == valorContrato
     */
    @Test
    @DisplayName("Validar ContratoCompraventa válido")
    void testValidacionCompraventaValido() {
        ContratoCompraventa c = new ContratoCompraventa(
                LocalDate.now(), "C003", "Compra de sillas", 5_000_000,
                1, EstadoContrato.PUBLICADO, TipoContrato.COMPRAVENTA,
                contratista, contratante, "Silla ergonómica", "Rimax",
                "OfficeMax", "SER-001", 500_000, 10);
        assertTrue(c.validarContrato());
    }

    /**
     * Verifica que un ContratoCompraventa con valores inconsistentes es inválido.
     * valorUnitario(500_000) * cantidad(10) = 5_000_000 != valorContrato(8_000_000)
     */
    @Test
    @DisplayName("Validar ContratoCompraventa inválido")
    void testValidacionCompraventaInvalido() {
        ContratoCompraventa c = new ContratoCompraventa(
                LocalDate.now(), "C003b", "Compra de sillas", 8_000_000,
                1, EstadoContrato.PUBLICADO, TipoContrato.COMPRAVENTA,
                contratista, contratante, "Silla", "Rimax",
                "OfficeMax", "SER-001", 500_000, 10);
        assertFalse(c.validarContrato());
    }

    // ===================== CONTRATOS - OBRA PÚBLICA =====================

    /**
     * Verifica que un ContratoObrasPublicas con datos completos es válido.
     */
    @Test
    @DisplayName("Validar ContratoObrasPublicas válido")
    void testValidacionObrasPublicasValido() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C004", "Construcción parque", 80_000_000,
                12, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "Calle 10 # 5-20 Tunja", 500.0);
        assertTrue(c.validarContrato());
    }

    /**
     * Verifica que un ContratoObrasPublicas sin ubicación es inválido.
     */
    @Test
    @DisplayName("Validar ContratoObrasPublicas sin ubicación inválido")
    void testValidacionObrasPublicasSinUbicacion() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C004b", "Construcción parque", 80_000_000,
                12, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "", 500.0);
        assertFalse(c.validarContrato());
    }

    // ===================== CRUD CONTRATOS =====================

    /**
     * Verifica que crearContratoValidado guarda el contrato si es válido.
     */
    @Test
    @DisplayName("crearContratoValidado con contrato válido lo guarda")
    void testCrearContratoValidadoValido() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C005", "Obra vial", 50_000_000,
                8, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "Av. Norte Tunja", 200.0);
        String resultado = contratoServicio.crearContratoValidado(c);
        assertTrue(resultado.contains("exitosamente"));
        assertNotNull(contratoServicio.consultarContrato("C005"));
    }

    /**
     * Verifica que crearContratoValidado rechaza un contrato inválido.
     */
    @Test
    @DisplayName("crearContratoValidado con contrato inválido lo rechaza")
    void testCrearContratoValidadoInvalido() {
        ContratoPrestacionServicios c = new ContratoPrestacionServicios(
                LocalDate.now(), "C006", "Consultoría", 99_000_000,
                6, EstadoContrato.PUBLICADO, TipoContrato.PRESENTACION_SERVICIOS,
                contratista, contratante, "Ingeniero", 2_000_000, 6);
        String resultado = contratoServicio.crearContratoValidado(c);
        assertTrue(resultado.contains("no es válido"));
        assertNull(contratoServicio.consultarContrato("C006"));
    }

    /**
     * Verifica que existeIdContrato retorna true para un ID ya registrado.
     */
    @Test
    @DisplayName("existeIdContrato retorna true para ID registrado")
    void testExisteIdContratoRegistrado() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C-DUP", "Obra test", 10_000_000,
                2, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "Calle 1", 50.0);
        contratoServicio.crearContrato(c);
        assertTrue(contratoServicio.existeIdContrato("C-DUP"));
    }

    /**
     * Verifica que existeIdContrato retorna false para un ID no registrado.
     */
    @Test
    @DisplayName("existeIdContrato retorna false para ID no registrado")
    void testExisteIdContratoNoRegistrado() {
        assertFalse(contratoServicio.existeIdContrato("NO-EXISTE"));
    }

    /**
     * Verifica que crear dos contratos con el mismo ID rechaza el segundo.
     */
    @Test
    @DisplayName("Crear contrato con ID duplicado es rechazado")
    void testCrearContratoIdDuplicado() {
        ContratoObrasPublicas c1 = new ContratoObrasPublicas(
                LocalDate.now(), "C-REP", "Obra 1", 10_000_000,
                2, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "Calle 1", 50.0);
        ContratoObrasPublicas c2 = new ContratoObrasPublicas(
                LocalDate.now(), "C-REP", "Obra 2", 20_000_000,
                3, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "Calle 2", 80.0);
        contratoServicio.crearContrato(c1);
        String resultado = contratoServicio.crearContratoValidado(c2);
        assertTrue(resultado.contains("Ya existe"));
    }

    /**
     * Verifica que actualizar un contrato existente cambia sus datos.
     */
    @Test
    @DisplayName("Actualizar contrato existente")
    void testActualizarContrato() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C007", "Obra peatonal", 30_000_000,
                4, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "Carrera 9 Tunja", 100.0);
        contratoServicio.crearContrato(c);
        String resultado = contratoServicio.actualizarContrato("C007", 35_000_000, 5);
        assertTrue(resultado.contains("actualizada"));
        assertEquals(35_000_000, contratoServicio.consultarContrato("C007").getValorContrato());
    }

    /**
     * Verifica que eliminar un contrato existente lo remueve del sistema.
     */
    @Test
    @DisplayName("Eliminar contrato existente")
    void testEliminarContrato() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C008", "Acueducto", 20_000_000,
                3, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "Rural Tunja", 50.0);
        contratoServicio.crearContrato(c);
        String resultado = contratoServicio.eliminar("C008");
        assertTrue(resultado.contains("eliminado"));
        assertNull(contratoServicio.consultarContrato("C008"));
    }

    // ===================== ESTADOS Y REPORTES =====================

    /**
     * Verifica que cambiarEstadoConReporte actualiza el estado y genera el reporte.
     */
    @Test
    @DisplayName("Cambiar estado genera reporte de interventoría")
    void testCambiarEstadoGeneraReporte() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C009", "Electrificación", 10_000_000,
                2, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "Sector rural", 80.0);
        contratoServicio.crearContrato(c);
        contratoServicio.cambiarEstadoConReporte("C009", EstadoContrato.EJECUCION,
                "Inicio de ejecución de la obra", reporteServicio);

        assertEquals(EstadoContrato.EJECUCION, c.getEstado());
        ArrayList<ReporteInventoria> reportes = reporteServicio.consultarReportesPorContrato("C009");
        assertEquals(1, reportes.size());
        assertEquals("Inicio de ejecución de la obra", reportes.get(0).getInforme());
    }

    /**
     * Verifica que cambiar el estado varias veces genera un reporte por cada cambio.
     */
    @Test
    @DisplayName("Múltiples cambios de estado generan múltiples reportes")
    void testMultiplesCambiosEstado() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C010", "Parque", 15_000_000,
                3, EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, "Calle 5", 60.0);
        contratoServicio.crearContrato(c);
        contratoServicio.cambiarEstadoConReporte("C010", EstadoContrato.LICITACION, "Apertura licitación", reporteServicio);
        contratoServicio.cambiarEstadoConReporte("C010", EstadoContrato.ADJUDICACION, "Adjudicado a contratista", reporteServicio);
        contratoServicio.cambiarEstadoConReporte("C010", EstadoContrato.EJECUCION, "Inicio de obra", reporteServicio);

        assertEquals(EstadoContrato.EJECUCION, c.getEstado());
        assertEquals(3, reporteServicio.consultarReportesPorContrato("C010").size());
    }
}
