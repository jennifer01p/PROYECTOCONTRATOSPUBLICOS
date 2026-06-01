package edu.uptc.servicio;

import edu.uptc.dominio.Contrato;
import edu.uptc.dominio.ReporteInventoria;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Servicio encargado de gestionar los reportes de interventoría del sistema.
 * Permite crear y consultar reportes asociados a cambios de estado en contratos.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class ReporteServicio {

    /** Lista de reportes de interventoría registrados en el sistema. */
    private ArrayList<ReporteInventoria> reportes;

    /**
     * Constructor que inicializa la lista de reportes.
     */
    public ReporteServicio() {
        this.reportes = new ArrayList<>();
    }

    /**
     * Crea y registra un nuevo reporte de interventoría para un contrato dado.
     *
     * @param contrato El contrato al que se asocia el reporte.
     * @param informe  Descripción del cambio o justificación del nuevo estado.
     * @return El reporte de interventoría creado.
     */
    public ReporteInventoria crearReporte(Contrato contrato, String informe) {
        ReporteInventoria reporte = new ReporteInventoria(informe, LocalDateTime.now(), contrato);
        reportes.add(reporte);
        return reporte;
    }

    /**
     * Retorna todos los reportes de interventoría registrados.
     *
     * @return Lista con todos los reportes.
     */
    public ArrayList<ReporteInventoria> consultarTodosLosReportes() {
        return reportes;
    }

    /**
     * Retorna los reportes asociados a un contrato específico según su ID.
     *
     * @param idContrato ID del contrato a filtrar.
     * @return Lista de reportes del contrato indicado.
     */
    public ArrayList<ReporteInventoria> consultarReportesPorContrato(String idContrato) {
        ArrayList<ReporteInventoria> resultado = new ArrayList<>();
        for (ReporteInventoria reporte : reportes) {
            if (reporte.getContrato() != null &&
                    idContrato.equals(reporte.getContrato().getId())) {
                resultado.add(reporte);
            }
        }
        return resultado;
    }
}
