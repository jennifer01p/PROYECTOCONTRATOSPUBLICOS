package edu.uptc.dominio;

import edu.uptc.enums.EstadoContrato;
import edu.uptc.enums.TipoContrato;

import java.time.LocalDate;

public class ContratoObrasPublicas extends Contrato{

    private String ubicacionObra;
    private double areaIntervencion;

    public ContratoObrasPublicas(LocalDate fechaCreacion,String id, String objeto, double valorContrato, int plazoContrato, EstadoContrato estado, TipoContrato tipoContrato,
                                 Contratista contratista, Contratante contratante, String ubicacionObra, double areaIntervencion) {
        super(fechaCreacion, id, objeto, valorContrato, plazoContrato, estado, tipoContrato, contratista, contratante);
        this.ubicacionObra = ubicacionObra;
        this.areaIntervencion = areaIntervencion;
    }
    public ContratoObrasPublicas(){}

    public double calcularValor(){
        return getValorContrato() ;
    }

    public boolean validarContrato(){
        if (areaIntervencion > 0 && ubicacionObra != null && !ubicacionObra.equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ContratoObrasPublicas{" +
                "ubicacionObra='" + ubicacionObra + '\'' +
                ", areaIntervencion=" + areaIntervencion +
                '}';
    }
}
