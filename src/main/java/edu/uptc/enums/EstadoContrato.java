package edu.uptc.enums;

/**
 * Enumeración que define los posibles estados de un contrato.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public enum EstadoContrato {
    /** El contrato ha sido publicado y está disponible para ofertas. */
    PUBLICADO,
    /** El contrato se encuentra en fase de licitación. */
    LICITACION,
    /** El contrato ha sido adjudicado a un contratista. */
    ADJUDICACION,
    /** El contrato se encuentra en ejecución. */
    EJECUCION,
    /** El contrato ha finalizado. */
    FINALIZADO
}
