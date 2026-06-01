package edu.uptc.dominio;

import java.time.LocalDate;
import edu.uptc.enums.EstadoContrato;
import edu.uptc.enums.TipoContrato;


public abstract class Contrato {
    protected LocalDate fechaCreacion;
    protected String id;
    protected String objeto;
    protected double valorContrato;
    protected int plazoContrato;
    protected EstadoContrato estado;
    protected TipoContrato tipoContrato;
    protected Contratista contratista;
    protected Contratante contratante;

    public Contrato (){}
    public Contrato(LocalDate fechaCreacion,String id,String objeto,double valorContrato, int plazoContrato, EstadoContrato estado,
                    TipoContrato tipoContrato,Contratista contratista, Contratante contratante){
        this.fechaCreacion = fechaCreacion;
        this.id = id;
        this.objeto = objeto;
        this.valorContrato = valorContrato;
        this.plazoContrato = plazoContrato;
        this.estado = estado ;
        this.tipoContrato = tipoContrato;
        this.contratista = contratista;
        this.contratante = contratante;
    }

    public abstract double calcularValor();


    public abstract boolean validarContrato();

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

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public Contratista getContratista() {
        return contratista;
    }

    public void setContratista(Contratista contratista) {
        this.contratista = contratista;
    }

    public Contratante getContratante() {
        return contratante;
    }

    public void setContratante(Contratante contratante) {
        this.contratante = contratante;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "fechaCreacion=" + fechaCreacion +
                ", objeto='" + objeto + '\'' +
                ", valorContrato=" + valorContrato +
                ", plazoContrato=" + plazoContrato +
                ", estado=" + estado +
                ", tipoContrato=" + tipoContrato +
                ", contratista=" + contratista +
                ", contratante=" + contratante +
                '}';
    }
}
