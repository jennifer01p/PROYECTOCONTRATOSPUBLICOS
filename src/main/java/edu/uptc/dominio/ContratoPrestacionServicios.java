package edu.uptc.dominio;

import edu.uptc.enums.EstadoContrato;
import edu.uptc.enums.TipoContrato;

import java.time.LocalDate;
import java.util.ArrayList;

public class ContratoPrestacionServicios extends Contrato{

    private String perfilRequerido;
    private ArrayList<String> entregables;
    private double honorarioMensual ;
    private int mesesDuracion;

    public ContratoPrestacionServicios(LocalDate fechaCreacion, String id,double valorContrato, int plazoContrato, EstadoContrato estado, TipoContrato tipoContrato,
                                       Contrato contratista, Contrato contratante, String perfilRequerido, double honorarioMensual,int mesesDuracion) {
        super(fechaCreacion,id, valorContrato, plazoContrato, estado, tipoContrato, contratista, contratante);
        this.perfilRequerido = perfilRequerido;
        this.entregables = new ArrayList<>();
        this.honorarioMensual = honorarioMensual;
        this.mesesDuracion = mesesDuracion;
    }

    public ContratoPrestacionServicios() {}

    public double calcularValor(){
        double total = this.honorarioMensual * this.mesesDuracion;
        return total ;
    }

    public boolean validarContrato(){
        if(calcularValor() == this.valorContrato){
           return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ContratoPrestacionServicios{" +
                "perfilRequerido='" + perfilRequerido + '\'' +
                ", entregables=" + entregables +
                ", honorarioMensual=" + honorarioMensual +
                ", mesesDuracion=" + mesesDuracion +
                '}';
    }
}
