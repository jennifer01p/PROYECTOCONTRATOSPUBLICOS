package edu.uptc.dominio;

import edu.uptc.enums.EstadoContrato;
import edu.uptc.enums.TipoContrato;

import java.time.LocalDate;

/**
 * Representa un contrato de compraventa, extendiendo la clase {@link Contrato}.
 * Incluye detalles específicos de los bienes a adquirir.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class ContratoCompraventa extends Contrato {
    /** Descripción del ítem o bien a adquirir. */
    private String item ;
    /** Marca del bien. */
    private String marca;
    /** Modelo del bien. */
    private String modelo;
    /** Número de serie del bien. */
    private String serie;
    /** Valor unitario del bien. */
    private double valorUnitario;
    /** Cantidad de bienes a adquirir. */
    private int cantidad;

    /**
     * Constructor por defecto de ContratoCompraventa.
     */
    public ContratoCompraventa() {}

    /**
     * Constructor para crear una nueva instancia de ContratoCompraventa.
     *
     * @param fechaCreacion Fecha de creación del contrato.
     * @param id Identificador único del contrato.
     * @param objeto Objeto o descripción general del contrato.
     * @param valorContrato Valor monetario total del contrato.
     * @param plazoContrato Plazo de ejecución del contrato en meses.
     * @param estado Estado actual del contrato.
     * @param tipoContrato Tipo específico de contrato (debe ser COMPRAVENTA).
     * @param contratista Contratista asociado.
     * @param contratante Contratante que gestiona.
     * @param item Descripción del ítem o bien.
     * @param marca Marca del bien.
     * @param modelo Modelo del bien.
     * @param serie Número de serie del bien.
     * @param valorUnitario Valor unitario del bien.
     * @param cantidad Cantidad de bienes.
     */
    public ContratoCompraventa(LocalDate fechaCreacion,String id, String objeto, double valorContrato, int plazoContrato, EstadoContrato estado, TipoContrato tipoContrato,
                               Contratista contratista, Contratante contratante, String item, String marca, String modelo, String serie,
                               double valorUnitario, int cantidad) {
        super(fechaCreacion,id, objeto, valorContrato, plazoContrato, estado, tipoContrato, contratista, contratante);
        this.item = item;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.valorUnitario = valorUnitario;
        this.cantidad = cantidad;
    }

    /**
     * Calcula el valor total del contrato de compraventa multiplicando el valor unitario por la cantidad.
     *
     * @return El valor calculado del contrato.
     */
    @Override
    public double calcularValor(){
        double valor = this.valorUnitario * this.cantidad;
        return valor;
    }

    /**
     * Valida el contrato de compraventa verificando que el valor calculado
     * (valor unitario * cantidad) coincida con el valor total del contrato.
     *
     * @return {@code true} si el valor calculado coincide con el valor del contrato, {@code false} en caso contrario.
     */
    @Override
    public boolean validarContrato(){
        if(calcularValor() == getValorContrato()){
            return true;
        }
        return false ;

    }

    /**
     * Retorna una representación en cadena de la información específica del contrato de compraventa.
     *
     * @return Una cadena que contiene los detalles del contrato de compraventa.
     */
    @Override
    public String toString() {
        return "ContratoCompraventa{" +
                "item='" + item + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", serie='" + serie + '\'' +
                ", valorUnitario=" + valorUnitario +
                ", cantidad=" + cantidad +
                '}';
    }
}
