package edu.uptc.dominio;

import edu.uptc.enums.TipoDocumento;
import edu.uptc.enums.TipoPersona;

/**
 * Representa un contratista en el sistema, extendiendo la información básica de un {@link Usuario}.
 * Un contratista es una entidad (persona natural o jurídica) que puede ser asignada a contratos.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class Contratista extends Usuario{
    /** Indica si el contratista es una entidad pública. */
    private boolean esEntidadPublica;
    /** Área principal de desempeño o especialización del contratista. */
    private String areaDesempeno;

    /**
     * Constructor para crear una nueva instancia de Contratista.
     *
     * @param tipoPersona Tipo de persona del contratista.
     * @param tipoDocumento Tipo de documento de identificación.
     * @param numeroDocumento Número de documento de identificación.
     * @param nombre Nombre completo o razón social.
     * @param correo Correo electrónico.
     * @param contraseña Contraseña.
     * @param telefono Número de teléfono.
     * @param direcccion Dirección.
     * @param ciudad Ciudad.
     * @param esEntidadPublica {@code true} si es una entidad pública, {@code false} en caso contrario.
     * @param areaDesempeno Área principal de desempeño.
     */
    public Contratista(TipoPersona tipoPersona, TipoDocumento tipoDocumento, String numeroDocumento, String nombre, String correo, String contraseña,
                       String telefono, String direcccion, String ciudad, boolean esEntidadPublica, String areaDesempeno) {
        super(tipoPersona, tipoDocumento, numeroDocumento, nombre, correo, contraseña, telefono, direcccion, ciudad);
        this.esEntidadPublica = esEntidadPublica;
        this.areaDesempeno = areaDesempeno;
    }

    /**
     * Constructor por defecto de Contratista.
     */
    public Contratista() {}

    /**
     * Verifica si el contratista es una entidad pública.
     *
     * @return {@code true} si es una entidad pública, {@code false} en caso contrario.
     */
    public boolean isEsEntidadPublica() {
        return esEntidadPublica;
    }

    /**
     * Establece si el contratista es una entidad pública.
     *
     * @param esEntidadPublica {@code true} si es una entidad pública, {@code false} en caso contrario.
     */
    public void setEsEntidadPublica(boolean esEntidadPublica) {
        this.esEntidadPublica = esEntidadPublica;
    }

    /**
     * Obtiene el área principal de desempeño o especialización del contratista.
     *
     * @return El área de desempeño.
     */
    public String getAreaDesempeno() {
        return areaDesempeno;
    }

    /**
     * Establece el área principal de desempeño o especialización del contratista.
     *
     * @param areaDesempeno La nueva área de desempeño.
     */
    public void setAreaDesempeno(String areaDesempeno) {
        this.areaDesempeno = areaDesempeno;
    }
}
