package edu.uptc.dominio;

import edu.uptc.enums.TipoDocumento;
import edu.uptc.enums.TipoPersona;

/**
 * Representa un administrador del sistema, extendiendo la información básica de un {@link Usuario}.
 * Los administradores tienen privilegios especiales para la gestión del sistema.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class Administrador extends Usuario{

    /**
     * Constructor para crear una nueva instancia de Administrador.
     *
     * @param tipoPersona Tipo de persona del administrador.
     * @param tipoDocumento Tipo de documento de identificación.
     * @param numeroDocumento Número de documento de identificación.
     * @param nombre Nombre completo o razón social.
     * @param correo Correo electrónico.
     * @param contraseña Contraseña.
     * @param telefono Número de teléfono.
     * @param direcccion Dirección.
     * @param ciudad Ciudad.
     */
    public Administrador(TipoPersona tipoPersona, TipoDocumento tipoDocumento, String numeroDocumento, String nombre, String correo, String contraseña, String telefono, String direcccion, String ciudad) {
        super(tipoPersona, tipoDocumento, numeroDocumento, nombre, correo, contraseña, telefono, direcccion, ciudad);
    }

    /**
     * Constructor por defecto de Administrador.
     */
    public Administrador() {
    }
}
