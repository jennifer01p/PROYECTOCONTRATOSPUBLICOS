package edu.uptc.servicio;

import edu.uptc.dominio.*;
import edu.uptc.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ContratoServicioTest {

    private ContratoServicio contratoServicio;
    private ReporteServicio reporteServicio;
    private Contratante contratante;
    private Contratista contratista;

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

    @Test
    void existeIdContratoTest() {
        ContratoObrasPublicas c = new ContratoObrasPublicas(LocalDate.now(), "C001", "Obra A", 10_000_000,
                3, EstadoContrato.PUBLICADO,
                TipoContrato.OBRA_PUBLICA, contratista, contratante,
                "Calle 1 Tunja", 100.0);
        contratoServicio.crearContrato(c);

        assertTrue(contratoServicio.existeIdContrato("C001"), "El ID C001 fue registrado, existeIdContrato debe retornar true");
    }


    @Test
    void crearContratoTest(){
        ContratoObrasPublicas c = new ContratoObrasPublicas(
                LocalDate.now(), "C002", "Pavimentación",
                20_000_000, 4, EstadoContrato.PUBLICADO,
                TipoContrato.OBRA_PUBLICA, contratista, contratante,
                "Av. Norte Tunja", 150.0);

        String resultado = contratoServicio.crearContrato(c);

        assertEquals("Contrato registrado con ID: C002", resultado, "El mensaje debe confirmar que el contrato C002 fue registrado");

        // Verificar que realmente quedó en la lista

        assertNotNull(contratoServicio.consultarContrato("C002"), "Después de crearContrato, consultarContrato no debe retornar null");
    }

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