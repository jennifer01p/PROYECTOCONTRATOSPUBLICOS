package edu.uptc.dominio;

import edu.uptc.enums.EstadoContrato;
import edu.uptc.enums.TipoContrato;

import java.time.LocalDate;

public class ContratoCompraventa extends Contrato {
    private String item ;
    private String marca;
    private String modelo;
    private String serie;
    private double valorUnitario;
    private int cantidad;

    public ContratoCompraventa() {}
    public ContratoCompraventa(LocalDate fechaCreacion, double valorContrato, int plazoContrato, EstadoContrato estado, TipoContrato tipoContrato,
                               Contrato contratista, Contrato contratante, String item, String marca, String modelo, String serie,
                               double valorUnitario, int cantidad) {
        super(fechaCreacion, valorContrato, plazoContrato, estado, tipoContrato, contratista, contratante);
        this.item = item;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.valorUnitario = valorUnitario;
        this.cantidad = cantidad;
    }

    public double calcularValor(){
        double valor = this.valorUnitario * this.cantidad;
        return valor;
    }

    public boolean validarContrato(){
        if(calcularValor() == getValorContrato()){
            return true;
        }
        return false ;

    }
}
