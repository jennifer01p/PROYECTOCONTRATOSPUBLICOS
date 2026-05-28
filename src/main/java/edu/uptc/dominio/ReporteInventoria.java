package edu.uptc.dominio;

import java.time.LocalDateTime;

public class ReporteInventoria {

    private String informe;
    private LocalDateTime fechaHora;
    private Contrato contrato;

    public ReporteInventoria(String informe, LocalDateTime fechaHora, Contrato contrato) {
        this.informe = informe;
        this.fechaHora = fechaHora;
        this.contrato = contrato;
    }

    public ReporteInventoria() {}

    @Override
    public String toString() {
        return "ReporteInventoria{" +
                "informe='" + informe + '\'' +
                ", fechaHora=" + fechaHora +
                ", contrato=" + contrato +
                '}';
    }
}
