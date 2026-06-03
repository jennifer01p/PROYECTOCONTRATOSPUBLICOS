package edu.uptc.dominio;

import edu.uptc.enums.TipoDocumento;
import edu.uptc.enums.TipoPersona;

/**
 * Representa un usuario genérico en el sistema.
 * Esta clase base contiene los atributos comunes a todos los tipos de usuarios.
 *
 * @author Jennifer, Jesus y Santiago
 * @version 1.0
 */
public class Usuario {
    /** Tipo de persona del usuario (natural o jurídica). */
    protected TipoPersona tipoPersona;
    /** Tipo de documento de identificación del usuario. */
    protected TipoDocumento tipoDocumento;
    /** Número de documento de identificación del usuario. */
    protected String numeroDocumento ;
    /** Nombre completo o razón social del usuario. */
    protected String nombre ;
    /** Correo electrónico del usuario. */
    protected String correo;
    /** Contraseña del usuario para acceder al sistema. */
    protected String contraseña ;
    /** Número de teléfono del usuario. */
    protected String telefono ;
    /** Dirección de residencia o domicilio fiscal del usuario. */
    protected String direcccion ;
    /** Ciudad de residencia o domicilio fiscal del usuario. */
    protected String ciudad ;

    /**
     * Constructor para crear una nueva instancia de Usuario con todos sus atributos.
     *
     * @param tipoPersona Tipo de persona del usuario.
     * @param tipoDocumento Tipo de documento de identificación.
     * @param numeroDocumento Número de documento de identificación.
     * @param nombre Nombre completo o razón social.
     * @param correo Correo electrónico.
     * @param contraseña Contraseña.
     * @param telefono Número de teléfono.
     * @param direcccion Dirección.
     * @param ciudad Ciudad.
     */
    public Usuario(TipoPersona tipoPersona, TipoDocumento tipoDocumento, String numeroDocumento , String nombre,
                   String correo,String contraseña ,String telefono,String direcccion,String ciudad ) {
        this.tipoPersona = tipoPersona;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.direcccion = direcccion;
        this.ciudad = ciudad;
    }

    /**
     * Constructor por defecto de Usuario.
     */
    public Usuario (){}

    /**
     * Obtiene el nombre completo o razón social del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre completo o razón social del usuario.
     *
     * @param nombre El nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el número de documento de identificación del usuario.
     *
     * @return El número de documento.
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Establece el número de documento de identificación del usuario.
     *
     * @param numeroDocumento El nuevo número de documento.
     */
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    /**
     * Obtiene el tipo de persona del usuario.
     *
     * @return El tipo de persona.
     */
    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    /**
     * Establece el tipo de persona del usuario.
     *
     * @param tipoPersona El nuevo tipo de persona.
     */
    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return El correo electrónico.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param correo El nuevo correo electrónico.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña.
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param contraseña La nueva contraseña.
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * Obtiene el número de teléfono del usuario.
     *
     * @return El número de teléfono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del usuario.
     *
     * @param telefono El nuevo número de teléfono.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la dirección del usuario.
     *
     * @return La dirección.
     */
    public String getDirecccion() {
        return direcccion;
    }

    /**
     * Establece la dirección del usuario.
     *
     * @param direcccion La nueva dirección.
     */
    public void setDirecccion(String direcccion) {
        this.direcccion = direcccion;
    }

    /**
     * Obtiene la ciudad del usuario.
     *
     * @return La ciudad.
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad del usuario.
     *
     * @param ciudad La nueva ciudad.
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Retorna una representación en cadena de la información del usuario.
     *
     * @return Una cadena que contiene los detalles del usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "tipoPersona=" + tipoPersona +
                ", tipoDocumento=" + tipoDocumento +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direcccion='" + direcccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                '}';
    }
}
