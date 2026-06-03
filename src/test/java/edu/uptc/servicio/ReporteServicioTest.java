package edu.uptc.servicio;

import edu.uptc.dominio.Contratante;
import edu.uptc.dominio.Contratista;
import edu.uptc.dominio.ContratoObrasPublicas;
import edu.uptc.dominio.ReporteInventoria;
import edu.uptc.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para {@link ReporteServicio}.
 * Verifica el correcto funcionamiento de la creación y consulta de reportes de interventoría.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
class ReporteServicioTest {

    /** Instancia del servicio de reportes a probar. */
    private ReporteServicio reporteServicio;

    /** Contratante de prueba utilizado en los tests. */
    private Contratante contratante;

    /** Contratista de prueba utilizado en los tests. */
    private Contratista contratista;

    /** Instancia del servicio de contratos para crear contratos de prueba. */
    private ContratoServicio contratoServicio;

    /**
     * Configuración inicial para cada prueba.
     * Se inicializan las instancias de los servicios y los objetos de contratante y contratista de prueba.
     */
    @BeforeEach
    void inicio() {
        reporteServicio = new ReporteServicio();
        contratoServicio = new ContratoServicio();

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
     * Prueba la creación de un reporte de interventoría.
     * Verifica que el reporte se agregue correctamente a la lista de reportes.
     */
    @Test
    @DisplayName("Crear reporte y agregarlo a la lista y valida que se agrego")
    void CrearReporteTest() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C001", "Construcción parque",
                50_000_000, 6, EstadoContrato.PUBLICADO,
                TipoContrato.OBRA_PUBLICA, contratista, contratante,
                "Calle 10 # 5-20 Tunja", 500.0);

        contratoServicio.crearContrato(c);

        reporteServicio.crearReporte(c, "Se inicia proceso de licitación");

        assertEquals(1, reporteServicio.consultarTodosLosReportes().size(), "Después de crearReporte debe haber exactamente 1 reporte en la lista"
        );
    }

    /**
     * Prueba la consulta de todos los reportes cuando la lista está vacía.
     * Verifica que se retorne una lista no nula y vacía.
     */
    @Test
    @DisplayName("Consultar todos los reportes cuando la lista está vacía")
    void ConsultarTodosLosReportesTest() {

        ArrayList<ReporteInventoria> resultado =
                reporteServicio.consultarTodosLosReportes();

        assertNotNull(resultado, "No debe retornar null, debe retornar la lista aunque esté vacía");

        assertTrue(resultado.isEmpty(), "La lista debe estar vacía porque no se llamó a crearReporte todavía");
    }


    /**
     * Prueba la consulta de todos los reportes verificando el número correcto de reportes.
     * Se crean múltiples reportes y se verifica que la cantidad retornada sea la esperada.
     */
    @Test
    @DisplayName("Consultar todos los reportes y verificar el número correcto")
    void ConsultarTodosLosReportesNumeroCorrectoTest() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C006", "Obra tamaño",
                10_000_000, 3, EstadoContrato.PUBLICADO,
                TipoContrato.OBRA_PUBLICA, contratista, contratante,
                "Av. Norte", 100.0);
        contratoServicio.crearContrato(c);

        reporteServicio.crearReporte(c, "Reporte 1: inicio licitación");
        reporteServicio.crearReporte(c, "Reporte 2: adjudicación");
        reporteServicio.crearReporte(c, "Reporte 3: inicio ejecución");

        assertEquals(
                3, reporteServicio.consultarTodosLosReportes().size(), "Deben retornarse exactamente 3 porque se llamó crearReporte 3 veces"
        );
    }




    /**
     * Prueba la consulta de reportes filtrados por un ID de contrato específico.
     * Se crean reportes para diferentes contratos y se verifica que solo se retornen
     * los reportes asociados al contrato solicitado.
     */
    @Test
    @DisplayName("Consultar reportes por ID de contrato")
    void ConsultarReportesPorContratoTest() {
        ContratoObrasPublicas cA = new ContratoObrasPublicas(
                LocalDate.now(), "C007A", "Obra A",
                10_000_000, 2, EstadoContrato.PUBLICADO,
                TipoContrato.OBRA_PUBLICA, contratista, contratante,
                "Calle A", 50.0);

        ContratoObrasPublicas cB = new ContratoObrasPublicas(
                LocalDate.now(), "C007B", "Obra B",
                15_000_000, 3, EstadoContrato.PUBLICADO,
                TipoContrato.OBRA_PUBLICA, contratista, contratante,
                "Calle B", 70.0);

        contratoServicio.crearContrato(cA);
        contratoServicio.crearContrato(cB);

        reporteServicio.crearReporte(cA, "Reporte 1 de Obra A");
        reporteServicio.crearReporte(cA, "Reporte 2 de Obra A");
        reporteServicio.crearReporte(cB, "Reporte 1 de Obra B");

        ArrayList<ReporteInventoria> reportesDeA = reporteServicio.consultarReportesPorContrato("C007A");

        assertEquals(2, reportesDeA.size(),
                "Deben retornar los 2 reportes asociados al contrato C007A");

    }


}
