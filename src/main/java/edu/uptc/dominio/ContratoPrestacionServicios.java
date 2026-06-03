package edu.uptc.dominio;

import edu.uptc.enums.EstadoContrato;
import edu.uptc.enums.TipoContrato;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Representa un contrato de prestación de servicios, extendiendo la clase {@link Contrato}.
 * Incluye detalles específicos como el perfil requerido, entregables, honorarios y duración.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class ContratoPrestacionServicios extends Contrato{

    /** Perfil profesional o técnico requerido para la prestación del servicio. */
    private String perfilRequerido;
    /** Lista de entregables asociados al contrato de prestación de servicios. */
    private ArrayList<String> entregables;
    /** Monto del honorario mensual a pagar por el servicio. */
    private double honorarioMensual ;
    /** Duración del contrato en meses. */
    private int mesesDuracion;

    /**
     * Constructor para crear una nueva instancia de ContratoPrestacionServicios.
     *
     * @param fechaCreacion Fecha de creación del contrato.
     * @param id Identificador único del contrato.
     * @param objeto Objeto o descripción general del contrato.
     * @param valorContrato Valor monetario total del contrato.
     * @param plazoContrato Plazo de ejecución del contrato en meses.
     * @param estado Estado actual del contrato.
     * @param tipoContrato Tipo específico de contrato (debe ser PRESTACION_SERVICIOS).
     * @param contratista Contratista asociado.
     * @param contratante Contratante que gestiona.
     * @param perfilRequerido Perfil profesional requerido.
     * @param honorarioMensual Honorario mensual.
     * @param mesesDuracion Duración del contrato en meses.
     */
    public ContratoPrestacionServicios(LocalDate fechaCreacion, String id, String objeto, double valorContrato, int plazoContrato, EstadoContrato estado, TipoContrato tipoContrato,
                                       Contratista contratista, Contratante contratante, String perfilRequerido, double honorarioMensual,int mesesDuracion) {
        super(fechaCreacion,id, objeto, valorContrato, plazoContrato, estado, tipoContrato, contratista, contratante);
        this.perfilRequerido = perfilRequerido;
        this.entregables = new ArrayList<>();
        this.honorarioMensual = honorarioMensual;
        this.mesesDuracion = mesesDuracion;
    }

    /**
     * Constructor por defecto de ContratoPrestacionServicios.
     */
    public ContratoPrestacionServicios() {}

    /**
     * Calcula el valor total del contrato de prestación de servicios multiplicando
     * el honorario mensual por la duración en meses.
     *
     * @return El valor calculado del contrato.
     */
    @Override
    public double calcularValor(){
        double total = this.honorarioMensual * this.mesesDuracion;
        return total ;
    }

    /**
     * Valida el contrato de prestación de servicios verificando que el valor calculado
     * (honorario mensual * meses de duración) coincida con el valor total del contrato.
     *
     * @return {@code true} si el valor calculado coincide con el valor del contrato, {@code false} en caso contrario.
     */
    @Override
    public boolean validarContrato(){
        if(calcularValor() == this.valorContrato){
           return true;
        }
        return false;
    }

    /**
     * Retorna una representación en cadena de la información específica del contrato de prestación de servicios.
     *
     * @return Una cadena que contiene los detalles del contrato de prestación de servicios.
     */
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
