package edu.uptc.dominio;

import java.time.LocalDate;
import edu.uptc.enums.EstadoContrato;
import edu.uptc.enums.TipoContrato;

/**
 * Representa un contrato genérico en el sistema.
 * Esta clase base contiene los atributos comunes a todos los tipos de contratos.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class Contrato {
    /** Fecha de creación del contrato. */
    protected LocalDate fechaCreacion;
    /** Identificador único del contrato. */
    protected String id;
    /** Objeto o descripción del contrato. */
    protected String objeto;
    /** Valor monetario total del contrato. */
    protected double valorContrato;
    /** Plazo de ejecución del contrato en meses. */
    protected int plazoContrato;
    /** Estado actual del contrato (ej. PUBLICADO, EN_EJECUCION, FINALIZADO). */
    protected EstadoContrato estado;
    /** Tipo específico de contrato (ej. PRESTACION_SERVICIOS, COMPRAVENTA). */
    protected TipoContrato tipoContrato;
    /** Contratista asociado a este contrato. */
    protected Contratista contratista;
    /** Contratante que gestiona este contrato. */
    protected Contratante contratante;

    /**
     * Constructor por defecto de Contrato.
     */
    public Contrato (){}

    /**
     * Constructor para crear una nueva instancia de Contrato con todos sus atributos.
     *
     * @param fechaCreacion Fecha de creación del contrato.
     * @param id Identificador único del contrato.
     * @param objeto Objeto o descripción del contrato.
     * @param valorContrato Valor monetario total del contrato.
     * @param plazoContrato Plazo de ejecución del contrato en meses.
     * @param estado Estado actual del contrato.
     * @param tipoContrato Tipo específico de contrato.
     * @param contratista Contratista asociado.
     * @param contratante Contratante que gestiona.
     */
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

    /**
     * Calcula el valor del contrato. Este método debe ser sobrescrito por las subclases
     * para implementar la lógica de cálculo específica de cada tipo de contrato.
     *
     * @return El valor calculado del contrato.
     */
    public double calcularValor(){
        return 0.0;
    }

    /**
     * Valida el contrato. Este método debe ser sobrescrito por las subclases
     * para implementar la lógica de validación específica de cada tipo de contrato.
     *
     * @return {@code true} si el contrato es válido, {@code false} en caso contrario.
     */
    public boolean validarContrato() {
        return false;
    }

    /**
     * Obtiene el valor monetario total del contrato.
     *
     * @return El valor del contrato.
     */
    public double getValorContrato() {
        return valorContrato;
    }

    /**
     * Obtiene el estado actual del contrato.
     *
     * @return El estado del contrato.
     */
    public EstadoContrato getEstado() {
        return estado;
    }

    /**
     * Establece el estado actual del contrato.
     *
     * @param estado El nuevo estado del contrato.
     */
    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }

    /**
     * Establece el valor monetario total del contrato.
     *
     * @param valorContrato El nuevo valor del contrato.
     */
    public void setValorContrato(double valorContrato) {
        this.valorContrato = valorContrato;
    }

    /**
     * Obtiene el plazo de ejecución del contrato en meses.
     *
     * @return El plazo del contrato.
     */
    public int getPlazoContrato() {
        return plazoContrato;
    }

    /**
     * Establece el plazo de ejecución del contrato en meses.
     *
     * @param plazoContrato El nuevo plazo del contrato.
     */
    public void setPlazoContrato(int plazoContrato) {
        this.plazoContrato = plazoContrato;
    }

    /**
     * Obtiene el identificador único del contrato.
     *
     * @return El ID del contrato.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el objeto o descripción del contrato.
     *
     * @return El objeto del contrato.
     */
    public String getObjeto() {
        return objeto;
    }

    /**
     * Establece el objeto o descripción del contrato.
     *
     * @param objeto El nuevo objeto del contrato.
     */
    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    /**
     * Obtiene el contratista asociado a este contrato.
     *
     * @return El contratista.
     */
    public Contratista getContratista() {
        return contratista;
    }

    /**
     * Establece el contratista asociado a este contrato.
     *
     * @param contratista El nuevo contratista.
     */
    public void setContratista(Contratista contratista) {
        this.contratista = contratista;
    }

    /**
     * Obtiene el contratante que gestiona este contrato.
     *
     * @return El contratante.
     */
    public Contratante getContratante() {
        return contratante;
    }

    /**
     * Establece el contratante que gestiona este contrato.
     *
     * @param contratante El nuevo contratante.
     */
    public void setContratante(Contratante contratante) {
        this.contratante = contratante;
    }

    /**
     * Obtiene el tipo específico de contrato.
     *
     * @return El tipo de contrato.
     */
    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    /**
     * Obtiene la fecha de creación del contrato.
     *
     * @return La fecha de creación.
     */
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Retorna una representación en cadena de la información del contrato.
     *
     * @return Una cadena que contiene los detalles del contrato.
     */
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
