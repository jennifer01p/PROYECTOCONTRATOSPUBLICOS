package edu.uptc.controlador;

import edu.uptc.dominio.*;
import edu.uptc.enums.EstadoContrato;
import edu.uptc.enums.TipoContrato;
import edu.uptc.servicio.ContratoServicio;
import edu.uptc.servicio.ReporteServicio;

import java.time.LocalDate;

/**
 * Controlador de contratos. Actúa como puente entre la vista y el servicio.
 * No contiene lógica de negocio ni interfaces gráficas.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class ContratoControlador {

    /** Servicio de contratos. */
    private ContratoServicio contratoServicio;

    /** Servicio de reportes de interventoría. */
    private ReporteServicio reporteServicio;

    /**
     * Constructor del controlador.
     *
     * @param contratoServicio Servicio de contratos.
     * @param reporteServicio  Servicio de reportes.
     */
    public ContratoControlador(ContratoServicio contratoServicio, ReporteServicio reporteServicio) {
        this.contratoServicio = contratoServicio;
        this.reporteServicio = reporteServicio;
    }

    /**
     * Crea un contrato de prestación de servicios.
     *
     * @param id              ID del contrato.
     * @param objeto          Objeto del contrato.
     * @param valorContrato   Valor total del contrato.
     * @param plazoContrato   Plazo en meses.
     * @param contratista     Contratista asignado.
     * @param contratante     Contratante que crea el contrato.
     * @param perfilRequerido Perfil profesional requerido.
     * @param honorarioMensual Honorario mensual.
     * @param mesesDuracion   Duración en meses.
     * @return Mensaje de resultado.
     */
    public String crearContratoPrestacionServicios(String id, String objeto, double valorContrato,
                                                    int plazoContrato, Contratista contratista,
                                                    Contratante contratante, String perfilRequerido,
                                                    double honorarioMensual, int mesesDuracion) {
        ContratoPrestacionServicios contrato = new ContratoPrestacionServicios(
                LocalDate.now(), id, objeto, valorContrato, plazoContrato,
                EstadoContrato.PUBLICADO, TipoContrato.PRESENTACION_SERVICIOS,
                contratista, contratante, perfilRequerido, honorarioMensual, mesesDuracion);
        return contratoServicio.crearContratoValidado(contrato);
    }

    /**
     * Crea un contrato de compraventa.
     *
     * @param id            ID del contrato.
     * @param objeto        Objeto del contrato.
     * @param valorContrato Valor total.
     * @param plazoContrato Plazo en meses.
     * @param contratista   Contratista asignado.
     * @param contratante   Contratante que crea el contrato.
     * @param item          Ítem o bien a adquirir.
     * @param marca         Marca.
     * @param modelo        Modelo.
     * @param serie         Serie.
     * @param valorUnitario Valor unitario del bien.
     * @param cantidad      Cantidad a adquirir.
     * @return Mensaje de resultado.
     */
    public String crearContratoCompraventa(String id, String objeto, double valorContrato,
                                            int plazoContrato, Contratista contratista,
                                            Contratante contratante, String item, String marca,
                                            String modelo, String serie,
                                            double valorUnitario, int cantidad) {
        ContratoCompraventa contrato = new ContratoCompraventa(
                LocalDate.now(), id, objeto, valorContrato, plazoContrato,
                EstadoContrato.PUBLICADO, TipoContrato.COMPRAVENTA,
                contratista, contratante, item, marca, modelo, serie, valorUnitario, cantidad);
        return contratoServicio.crearContratoValidado(contrato);
    }

    /**
     * Crea un contrato de obra pública.
     *
     * @param id               ID del contrato.
     * @param objeto           Objeto del contrato.
     * @param valorContrato    Valor total.
     * @param plazoContrato    Plazo en meses.
     * @param contratista      Contratista asignado.
     * @param contratante      Contratante que crea el contrato.
     * @param ubicacionObra    Ubicación de la obra.
     * @param areaIntervencion Área de intervención en m².
     * @return Mensaje de resultado.
     */
    public String crearContratoObrasPublicas(String id, String objeto, double valorContrato,
                                              int plazoContrato, Contratista contratista,
                                              Contratante contratante, String ubicacionObra,
                                              double areaIntervencion) {
        ContratoObrasPublicas contrato = new ContratoObrasPublicas(
                LocalDate.now(), id, objeto, valorContrato, plazoContrato,
                EstadoContrato.PUBLICADO, TipoContrato.OBRA_PUBLICA,
                contratista, contratante, ubicacionObra, areaIntervencion);
        return contratoServicio.crearContratoValidado(contrato);
    }

    /**
     * Consulta un contrato por su ID.
     *
     * @param id ID del contrato.
     * @return El contrato encontrado o null.
     */
    public Contrato consultarContrato(String id) {
        return contratoServicio.consultarContrato(id);
    }

    /**
     * Actualiza el valor y plazo de un contrato.
     *
     * @param id            ID del contrato.
     * @param valorContrato Nuevo valor.
     * @param plazo         Nuevo plazo.
     * @return Mensaje de resultado.
     */
    public String actualizarContrato(String id, double valorContrato, int plazo) {
        return contratoServicio.actualizarContrato(id, valorContrato, plazo);
    }

    /**
     * Elimina un contrato por su ID.
     *
     * @param id ID del contrato.
     * @return Mensaje de resultado.
     */
    public String eliminarContrato(String id) {
        return contratoServicio.eliminar(id);
    }

    /**
     * Cambia el estado de un contrato y genera el reporte de interventoría.
     *
     * @param id      ID del contrato.
     * @param estado  Nuevo estado.
     * @param informe Informe que justifica el cambio.
     * @return Mensaje de resultado.
     */
    public String cambiarEstadoContrato(String id, EstadoContrato estado, String informe) {
        return contratoServicio.cambiarEstadoConReporte(id, estado, informe, reporteServicio);
    }

    /**
     * Verifica si ya existe un contrato con el ID dado.
     *
     * @param id ID a verificar.
     * @return {@code true} si ya existe, {@code false} si no.
     */
    public boolean existeIdContrato(String id) {
        return contratoServicio.existeIdContrato(id);
    }

    /**
     * Retorna el servicio de contratos.
     *
     * @return Instancia de {@link ContratoServicio}.
     */
    public ContratoServicio getContratoServicio() {
        return contratoServicio;
    }
}
