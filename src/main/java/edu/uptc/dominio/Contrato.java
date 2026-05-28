package edu.uptc.dominio;

import java.time.LocalDate;
import edu.uptc.enums.EstadoContrato;
import edu.uptc.enums.TipoContrato;


public class Contrato {
    protected LocalDate fechaCreacion;
    protected String id;
    protected double valorContrato;
    protected int plazoContrato;
    protected EstadoContrato estado;
    protected TipoContrato tipoContrato;
    protected Contrato contratista;
    protected Contrato contratante;

    public Contrato (){}
    public Contrato(LocalDate fechaCreacion,String id,double valorContrato, int plazoContrato, EstadoContrato estado,
                    TipoContrato tipoContrato,Contrato contratista, Contrato contratante){
        this.fechaCreacion = fechaCreacion;
        this.id = id;
        this.valorContrato = valorContrato;
        this.plazoContrato = plazoContrato;
        this.estado = estado ;
        this.tipoContrato = tipoContrato;
        this.contratista = contratista;
        this.contratante = contratante;
    }

    public double calcularValor(){
        return 0.0;
    }

    public boolean validarContrato() {
        return false;
    }
    public double getValorContrato() {
        return valorContrato;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }

    public void setValorContrato(double valorContrato) {
        this.valorContrato = valorContrato;
    }

    public int getPlazoContrato() {
        return plazoContrato;
    }

    public void setPlazoContrato(int plazoContrato) {
        this.plazoContrato = plazoContrato;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "fechaCreacion=" + fechaCreacion +
                ", valorContrato=" + valorContrato +
                ", plazoContrato=" + plazoContrato +
                ", estado=" + estado +
                ", tipoContrato=" + tipoContrato +
                ", contratista=" + contratista +
                ", contratante=" + contratante +
                '}';
    }
}
