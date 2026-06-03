package edu.uptc.dominio;

import edu.uptc.enums.EstadoContrato;
import edu.uptc.enums.TipoContrato;

import java.time.LocalDate;

/**
 * Representa un contrato de obras públicas, extendiendo la clase {@link Contrato}.
 * Incluye detalles específicos de la ubicación y área de intervención de la obra.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class ContratoObrasPublicas extends Contrato{

    /** Ubicación geográfica donde se realizará la obra. */
    private String ubicacionObra;
    /** Área de intervención de la obra en metros cuadrados. */
    private double areaIntervencion;

    /**
     * Constructor para crear una nueva instancia de ContratoObrasPublicas.
     *
     * @param fechaCreacion Fecha de creación del contrato.
     * @param id Identificador único del contrato.
     * @param objeto Objeto o descripción general del contrato.
     * @param valorContrato Valor monetario total del contrato.
     * @param plazoContrato Plazo de ejecución del contrato en meses.
     * @param estado Estado actual del contrato.
     * @param tipoContrato Tipo específico de contrato (debe ser OBRA_PUBLICA).
     * @param contratista Contratista asociado.
     * @param contratante Contratante que gestiona.
     * @param ubicacionObra Ubicación de la obra.
     * @param areaIntervencion Área de intervención en m².
     */
    public ContratoObrasPublicas(LocalDate fechaCreacion,String id, String objeto, double valorContrato, int plazoContrato, EstadoContrato estado, TipoContrato tipoContrato,
                                 Contratista contratista, Contratante contratante, String ubicacionObra, double areaIntervencion) {
        super(fechaCreacion, id, objeto, valorContrato, plazoContrato, estado, tipoContrato, contratista, contratante);
        this.ubicacionObra = ubicacionObra;
        this.areaIntervencion = areaIntervencion;
    }

    /**
     * Constructor por defecto de ContratoObrasPublicas.
     */
    public ContratoObrasPublicas(){}

    /**
     * Calcula el valor del contrato de obras públicas. En este caso, simplemente retorna el valor del contrato establecido.
     *
     * @return El valor del contrato.
     */
    @Override
    public double calcularValor(){
        return getValorContrato() ;
    }

    /**
     * Valida el contrato de obras públicas verificando que el área de intervención sea mayor que cero
     * y que la ubicación de la obra no sea nula o vacía.
     *
     * @return {@code true} si el contrato es válido, {@code false} en caso contrario.
     */
    @Override
    public boolean validarContrato(){
        if (areaIntervencion > 0 && ubicacionObra != null && !ubicacionObra.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * Retorna una representación en cadena de la información específica del contrato de obras públicas.
     *
     * @return Una cadena que contiene los detalles del contrato de obras públicas.
     */
    @Override
    public String toString() {
        return "ContratoObrasPublicas{" +
                "ubicacionObra='" + ubicacionObra + '\'' +
                ", areaIntervencion=" + areaIntervencion +
                '}';
    }
}
