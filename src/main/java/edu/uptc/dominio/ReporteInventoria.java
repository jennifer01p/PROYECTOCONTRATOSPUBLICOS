package edu.uptc.dominio;

import java.time.LocalDateTime;

/**
 * Representa un reporte de interventoría generado al cambiar el estado de un contrato.
 * Registra el informe justificativo, la fecha/hora y el contrato modificado.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class ReporteInventoria {

    /** Informe que justifica el cambio de estado del contrato. */
    private String informe;

    /** Fecha y hora en que se generó el reporte. */
    private LocalDateTime fechaHora;

    /** Contrato al que pertenece este reporte. */
    private Contrato contrato;

    /**
     * Constructor completo del reporte de interventoría.
     *
     * @param informe   Descripción del cambio realizado.
     * @param fechaHora Fecha y hora de creación.
     * @param contrato  Contrato asociado al reporte.
     */
    public ReporteInventoria(String informe, LocalDateTime fechaHora, Contrato contrato) {
        this.informe = informe;
        this.fechaHora = fechaHora;
        this.contrato = contrato;
    }

    /** Constructor vacío. */
    public ReporteInventoria() {}

    /**
     * Obtiene el informe del reporte.
     * @return Texto del informe.
     */
    public String getInforme() { return informe; }

    /**
     * Establece el informe del reporte.
     * @param informe Nuevo texto del informe.
     */
    public void setInforme(String informe) { this.informe = informe; }

    /**
     * Obtiene la fecha y hora del reporte.
     * @return Fecha y hora de creación.
     */
    public LocalDateTime getFechaHora() { return fechaHora; }

    /**
     * Establece la fecha y hora del reporte.
     * @param fechaHora Nueva fecha y hora.
     */
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    /**
     * Obtiene el contrato asociado al reporte.
     * @return Contrato relacionado.
     */
    public Contrato getContrato() { return contrato; }

    /**
     * Establece el contrato del reporte.
     * @param contrato Nuevo contrato.
     */
    public void setContrato(Contrato contrato) { this.contrato = contrato; }

    @Override
    public String toString() {
        return "ReporteInventoria{" +
                "informe='" + informe + '\'' +
                ", fechaHora=" + fechaHora +
                ", contrato=" + (contrato != null ? contrato.getId() : "N/A") +
                '}';
    }
}
