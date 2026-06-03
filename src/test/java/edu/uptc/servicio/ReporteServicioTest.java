package edu.uptc.servicio;

import edu.uptc.dominio.Contratante;
import edu.uptc.dominio.Contratista;
import edu.uptc.dominio.ContratoObrasPublicas;
import edu.uptc.dominio.ReporteInventoria;
import edu.uptc.enums.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReporteServicioTest {

    private ReporteServicio reporteServicio = new ReporteServicio();

    private Contratante contratante;

    private Contratista contratista;

    private ContratoServicio contratoServicio = new ContratoServicio();

    @BeforeEach
    void inicio() {


        contratante = new Contratante(
                TipoPersona.JURIDICA, TipoDocumento.NIT,
                "900123456", "Alcaldía de Tunja",
                "contratante@tunja.gov.co", "clave123",
                "6087412580", "Calle 19", "Tunja",
                "Gobierno", NivelEntidad.MUNICIPAL, "ENT-001");

        // Contratista de prueba
        contratista = new Contratista(
                TipoPersona.NATURAL, TipoDocumento.CC,
                "1234567890", "Carlos Pérez",
                "carlos@gmail.com", "clave456",
                "3109876543", "Carrera 5", "Tunja",
                false, "Ingeniería Civil");
    }


    @Test
    //Crear reporte y agregarlo a la lista y valida que se agrego
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

    @Test
    void ConsultarTodosLosReportesTest() {

        ArrayList<ReporteInventoria> resultado =
                reporteServicio.consultarTodosLosReportes();

        assertNotNull(resultado, "No debe retornar null, debe retornar la lista aunque esté vacía");

        assertTrue(resultado.isEmpty(), "La lista debe estar vacía porque no se llamó a crearReporte todavía");
    }


    @Test
    //verificar que son el numero de reportes que se crearon
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




    @Test
    //Para validar si se esta retornando los reportes con el id
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