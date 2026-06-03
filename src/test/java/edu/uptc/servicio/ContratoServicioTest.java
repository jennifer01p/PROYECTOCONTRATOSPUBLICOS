package edu.uptc.servicio;

import edu.uptc.dominio.*;
import edu.uptc.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para {@link ContratoServicio}.
 * Verifica el correcto funcionamiento de la gestión de contratos, incluyendo
 * la creación, consulta, actualización, eliminación y cambio de estado.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
class ContratoServicioTest {

    /** Instancia del servicio de contratos a probar. */
    private ContratoServicio contratoServicio;
    /** Instancia del servicio de reportes, utilizada para pruebas de cambio de estado con reporte. */
    private ReporteServicio reporteServicio;
    /** Contratante de prueba utilizado en los tests. */
    private Contratante contratante;
    /** Contratista de prueba utilizado en los tests. */
    private Contratista contratista;

    /**
     * Configuración inicial para cada prueba.
     * Se inicializan las instancias de los servicios y los objetos de contratante y contratista de prueba.
     */
    @BeforeEach
    void setUp() {
        contratoServicio = new ContratoServicio();
        reporteServicio  = new ReporteServicio();

        contratante = new Contratante(
                TipoPersona.JURIDICA, TipoDocumento.NIT,
                "900123456", "Alcaldía de Tunja",
                "contratante@tunja.gov.co", "clave123",
                "6087412580", "Calle 19", "Tunja",
                "Gobierno", NivelEntidad.MUNICIPAL, "ENT-001");

        contratista = new Contratista(
                TipoPersona.NATURAL, TipoDocumento.CC,
                "1234567890", "Carlos Pérez",
                "carlos@gmail.com", "clave456",
                "3109876543", "Carrera 5", "Tunja",
                false, "Ingeniería Civil");
    }

    /**
     * Prueba el método {@link ContratoServicio#existeIdContrato(String)}.
     * Verifica que el método retorne {@code true} cuando un ID de contrato ya ha sido registrado.
     */
    @Test
    void existeIdContratoTest() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(LocalDate.now(), "C001", "Obra A", 10_000_000,
                3, EstadoContrato.PUBLICADO,
                TipoContrato.OBRA_PUBLICA, contratista, contratante,
                "Calle 1 Tunja", 100.0);
        contratoServicio.crearContrato(c);

        assertTrue(contratoServicio.existeIdContrato("C001"), "El ID C001 fue registrado, existeIdContrato debe retornar true");
    }


    /**
     * Prueba el método {@link ContratoServicio#crearContrato(Contrato)}.
     * Verifica que un contrato se cree exitosamente y que pueda ser consultado posteriormente.
     */
    @Test
    void crearContratoTest(){
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C002", "Pavimentación",
                20_000_000, 4, EstadoContrato.PUBLICADO,
                TipoContrato.OBRA_PUBLICA, contratista, contratante,
                "Av. Norte Tunja", 150.0);

        String resultado = contratoServicio.crearContrato(c);

        assertEquals("Contrato registrado con ID: C002", resultado, "El mensaje debe confirmar que el contrato C002 fue registrado");

        assertNotNull(contratoServicio.consultarContrato("C002"), "Después de crearContrato, consultarContrato no debe retornar null");
    }

    /**
     * Prueba el método {@link ContratoServicio#crearContratoValidado(Contrato)}.
     * Verifica que un contrato válido (con valores que coinciden) se cree exitosamente.
     */
    @Test
    void crearContratoValidadoTest() {
        ContratoPrestacionServicios c = new ContratoPrestacionServicios(
                LocalDate.now(), "C004", "Consultoría TI",
                12_000_000, 6, EstadoContrato.PUBLICADO,
                TipoContrato.PRESENTACION_SERVICIOS,
                contratista, contratante,
                "Ingeniero de Sistemas", 2_000_000, 6);

        String resultado = contratoServicio.crearContratoValidado(c);

        assertEquals("Contrato creado exitosamente con ID: C004", resultado, "Con honorario 2M * 6 meses = 12M que coincide con el valor, debe ser válido");

        assertNotNull(contratoServicio.consultarContrato("C004"), "El contrato debe quedar registrado en la lista");
    }

    /**
     * Prueba el método {@link ContratoServicio#consultarContrato(String)}.
     * Verifica que se pueda encontrar un contrato existente por su ID.
     */
    @Test
    void consultarContratoTest() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C007", "Obra consultada",
                15_000_000, 3, EstadoContrato.PUBLICADO,
                TipoContrato.OBRA_PUBLICA, contratista, contratante,
                "Calle 7", 60.0);
        contratoServicio.crearContrato(c);

        Contrato encontrado = contratoServicio.consultarContrato("C007");

        assertNotNull(encontrado, "El contrato C007 fue registrado, no debe retornar null");

        assertEquals("C007", encontrado.getId(), "El ID del contrato retornado debe ser C007");
    }

    /**
     * Prueba el método {@link ContratoServicio#actualizarContrato(String, double, int)}.
     * Verifica que el valor y el plazo de un contrato se actualicen correctamente.
     */
    @Test
    void actualizarContratoTest() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C008", "Obra a actualizar",
                10_000_000, 3, EstadoContrato.PUBLICADO,
                TipoContrato.OBRA_PUBLICA, contratista, contratante,
                "Calle 8", 50.0);
        contratoServicio.crearContrato(c);

        String resultado = contratoServicio.actualizarContrato("C008", 20_000_000, 6);

        assertEquals(
                "Información del contrato actualizada correctamente.",
                resultado,
                "Debe retornar el mensaje de actualización exitosa"
        );
        assertEquals(20_000_000, c.getValorContrato(), 0.01,
                "El valor del contrato debe haberse actualizado a 20 millones");
        assertEquals(6, c.getPlazoContrato(),
                "El plazo del contrato debe haberse actualizado a 6 meses");
    }


    /**
     * Prueba el método {@link ContratoServicio#eliminar(String)}.
     * Verifica que un contrato se elimine exitosamente del sistema.
     */
    @Test
    void testEliminar_Exitoso() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C009", "Obra a eliminar",
                8_000_000, 2, EstadoContrato.PUBLICADO,
                TipoContrato.OBRA_PUBLICA, contratista, contratante,
                "Calle 9", 30.0);
        contratoServicio.crearContrato(c);

        String resultado = contratoServicio.eliminar("C009");

        assertEquals("Contrato eliminado correctamente.", resultado, "Debe retornar el mensaje de eliminación exitosa");

        assertNull(contratoServicio.consultarContrato("C009"), "Después de eliminar, consultarContrato debe retornar null");
    }

    /**
     * Prueba el método {@link ContratoServicio#cambiarEstadoContrato(String, EstadoContrato)}.
     * Verifica que el estado de un contrato se actualice correctamente.
     */
    @Test
    void CambiarEstadoContratoExitosoTest() {

        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C010", "Obra con estado",
                12_000_000, 4, EstadoContrato.PUBLICADO,
                TipoContrato.OBRA_PUBLICA, contratista, contratante,
                "Calle 10", 70.0);
        contratoServicio.crearContrato(c);

        String resultado = contratoServicio.cambiarEstadoContrato("C010", EstadoContrato.LICITACION);

        assertTrue(resultado.contains("LICITACION"), "El mensaje debe indicar que el estado cambió a LICITACION");

        assertEquals(EstadoContrato.LICITACION, c.getEstado(), "getEstado() debe retornar LICITACION después del cambio"
        );
    }
}
