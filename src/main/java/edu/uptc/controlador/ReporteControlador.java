package edu.uptc.controlador;

import edu.uptc.dominio.ReporteInventoria;
import edu.uptc.servicio.ReporteServicio;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Controlador encargado de gestionar la consulta de reportes de interventoría
 * mediante cuadros de diálogo JOptionPane.
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
     * Muestra todos los reportes de interventoría registrados en el sistema.
     */
    public void consultarTodosLosReportes() {
        ArrayList<ReporteInventoria> reportes = reporteServicio.consultarTodosLosReportes();
        if (reportes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay reportes registrados.", "Reportes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("=== Reportes de Interventoría ===\n\n");
        for (ReporteInventoria r : reportes) {
            sb.append("Contrato: ").append(r.getContrato() != null ? r.getContrato().getId() : "N/A").append("\n");
            sb.append("Fecha/Hora: ").append(r.getFechaHora()).append("\n");
            sb.append("Informe: ").append(r.getInforme()).append("\n");
            sb.append("Estado actual: ").append(r.getContrato() != null ? r.getContrato().getEstado() : "N/A").append("\n");
            sb.append("-----------------------------\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Reportes de Interventoría", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra los reportes de interventoría de un contrato específico.
     */
    public void consultarReportesPorContrato() {
        String id = JOptionPane.showInputDialog("Ingrese el ID del contrato a consultar:");
        if (id == null || id.isBlank()) return;
        ArrayList<ReporteInventoria> reportes = reporteServicio.consultarReportesPorContrato(id);
        if (reportes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron reportes para el contrato: " + id, "Reportes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("=== Reportes del Contrato " + id + " ===\n\n");
        for (ReporteInventoria r : reportes) {
            sb.append("Fecha/Hora: ").append(r.getFechaHora()).append("\n");
            sb.append("Informe: ").append(r.getInforme()).append("\n");
            sb.append("Estado: ").append(r.getContrato() != null ? r.getContrato().getEstado() : "N/A").append("\n");
            sb.append("-----------------------------\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Reportes", JOptionPane.INFORMATION_MESSAGE);
    }
}
