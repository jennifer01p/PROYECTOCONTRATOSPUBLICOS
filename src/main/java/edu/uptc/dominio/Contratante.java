package edu.uptc.dominio;

import edu.uptc.enums.NivelEntidad;
import edu.uptc.enums.TipoDocumento;
import edu.uptc.enums.TipoPersona;

/**
 * Representa un contratante en el sistema, extendiendo la información básica de un {@link Usuario}.
 * Un contratante es una entidad (persona natural o jurídica) que publica y gestiona contratos.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class Contratante extends Usuario{

    /** Sector al que pertenece la entidad contratante. */
    private String sector;
    /** Nivel de la entidad contratante (ej. NACIONAL, DEPARTAMENTAL, MUNICIPAL). */
    private NivelEntidad nivelEntidad;
    /** Código único de la entidad contratante. */
    private String codigoEntidad;

    /**
     * Constructor para crear una nueva instancia de Contratante.
     *
     * @param tipoPersona Tipo de persona del contratante.
     * @param tipoDocumento Tipo de documento de identificación.
     * @param numeroDocumento Número de documento de identificación.
     * @param nombre Nombre completo o razón social.
     * @param correo Correo electrónico.
     * @param contraseña Contraseña.
     * @param telefono Número de teléfono.
     * @param direcccion Dirección.
     * @param ciudad Ciudad.
     * @param sector Sector al que pertenece la entidad.
     * @param nivelEntidad Nivel de la entidad.
     * @param codigoEntidad Código único de la entidad.
     */
    public Contratante(TipoPersona tipoPersona, TipoDocumento tipoDocumento, String numeroDocumento, String nombre, String correo, String contraseña,
                       String telefono, String direcccion, String ciudad, String sector, NivelEntidad nivelEntidad,String codigoEntidad) {
        super(tipoPersona, tipoDocumento, numeroDocumento, nombre, correo, contraseña, telefono, direcccion, ciudad);
        this.sector = sector;
        this.nivelEntidad = nivelEntidad;
        this.codigoEntidad = codigoEntidad;
    }

    /**
     * Constructor por defecto de Contratante.
     */
    public Contratante() {}

    /**
     * Obtiene el sector al que pertenece la entidad contratante.
     *
     * @return El sector de la entidad.
     */
    public String getSector() {
        return sector;
    }

    /**
     * Establece el sector al que pertenece la entidad contratante.
     *
     * @param sector El nuevo sector de la entidad.
     */
    public void setSector(String sector) {
        this.sector = sector;
    }

    /**
     * Obtiene el nivel de la entidad contratante.
     *
     * @return El nivel de la entidad.
     */
    public NivelEntidad getNivelEntidad() {
        return nivelEntidad;
    }

    /**
     * Establece el nivel de la entidad contratante.
     *
     * @param nivelEntidad El nuevo nivel de la entidad.
     */
    public void setNivelEntidad(NivelEntidad nivelEntidad) {
        this.nivelEntidad = nivelEntidad;
    }

    /**
     * Obtiene el código único de la entidad contratante.
     *
     * @return El código de la entidad.
     */
    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    /**
     * Establece el código único de la entidad contratante.
     *
     * @param codigoEntidad El nuevo código de la entidad.
     */
    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }
}
