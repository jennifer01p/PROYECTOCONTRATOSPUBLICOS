package edu.uptc.controlador;

import edu.uptc.dominio.ReporteInventoria;
import edu.uptc.servicio.ReporteServicio;

import java.util.ArrayList;

/**
 * Controlador de reportes. Actúa como puente entre la vista y el servicio.
 * No contiene lógica de negocio ni interfaces gráficas.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class ReporteControlador {

    /** Servicio de reportes de interventoría. */
    private ReporteServicio reporteServicio;

    /**
     * Constructor del controlador de reportes.
     *
     * @param reporteServicio Servicio de reportes.
     */
    public ReporteControlador(ReporteServicio reporteServicio) {
        this.reporteServicio = reporteServicio;
    }

    /**
     * Consulta todos los reportes de interventoría registrados.
     *
     * @return Lista de todos los reportes.
     */
    public ArrayList<ReporteInventoria> consultarTodos() {
        return reporteServicio.consultarTodosLosReportes();
    }

    /**
     * Consulta los reportes de un contrato específico.
     *
     * @param idContrato ID del contrato.
     * @return Lista de reportes del contrato.
     */
    public ArrayList<ReporteInventoria> consultarPorContrato(String idContrato) {
        return reporteServicio.consultarReportesPorContrato(idContrato);
    }
}
